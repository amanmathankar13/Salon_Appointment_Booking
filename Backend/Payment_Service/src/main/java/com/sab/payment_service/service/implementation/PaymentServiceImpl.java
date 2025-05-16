package com.sab.payment_service.service.implementation;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sab.payment_service.domain.PaymentMethod;
import com.sab.payment_service.domain.PaymentOrderStatus;
import com.sab.payment_service.entity.PaymentOrder;
import com.sab.payment_service.payload.dto.BookingDTO;
import com.sab.payment_service.payload.dto.UserDTO;
import com.sab.payment_service.payload.response.PaymentLinkResponse;
import com.sab.payment_service.repository.PaymentOrderRepository;
import com.sab.payment_service.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Value("${stripe.api.secret}")
    private String stripeApiSecret;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException {
        Long amount = (long)booking.getTotalPrice();
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setBookingId(booking.getId());
        paymentOrder.setUserId(user.getId());
        paymentOrder.setSalonId(booking.getSalonId());
        
        PaymentOrder savedOrder = paymentOrderRepository.save(paymentOrder);
        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink paymentLink = createRazorpayPaymentLink(user,savedOrder.getAmount(),savedOrder.getId());
            String paymentUrl = paymentLink.get("short_url");
            String paymentUrlId = paymentLink.get("id");
            paymentLinkResponse.setPaymentLink(paymentUrl);
            paymentLinkResponse.setPaymentLinkId(paymentUrlId);
            savedOrder.setPaymentLinkId(paymentUrlId);
            paymentOrderRepository.save(savedOrder);
        }
        else{
            String paymentLink = createStripePaymentLink(user,savedOrder.getAmount(),savedOrder.getId());
            paymentLinkResponse.setPaymentLink(paymentLink);
            paymentOrderRepository.save(savedOrder);
        }
        return paymentLinkResponse;

    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElseThrow(()-> new RuntimeException("Payment Order Not Found"));
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String id) {
        return paymentOrderRepository.findByPaymentLinkId(id);
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException {
        Long amt = amount*100;
        RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey,razorpayApiSecret);
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amt);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getName());
        customer.put("email", user.getEmail());

        JSONObject notify = new JSONObject();
        notify.put("email", true);

        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("customer", customer);
        paymentLinkRequest.put("reminder_enable", true);
        paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success" + orderId);
        paymentLinkRequest.put("callback_method", "get");
        PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
        return paymentLink;
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        SessionCreateParams sessionCreateParams = SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
        .setCancelUrl("http://localhost:3000/payment-cancel/"+orderId)
        .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
        .setCurrency("usd").setUnitAmount(amount*100)
        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
        .setName("Salon Appointment booking").build()
        ).build()).build()
        ).build();

        Session session = new Session();
        session = Session.create(sessionCreateParams);
        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {
        if(paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.PENDING)) {
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {
                RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
                Payment payment = razorpayClient.payments.fetch(paymentId);
                String status = payment.get("status");
                if(status.equals("captured")){
                    // produce kafka event
                    paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);
                    return true;
                }
                return false;
            }
            else{
                paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }
        }
        return false;
    }

   
}
