package com.sab.payment_service.service;


import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.sab.payment_service.domain.PaymentMethod;
import com.sab.payment_service.entity.PaymentOrder;
import com.sab.payment_service.payload.dto.BookingDTO;
import com.sab.payment_service.payload.dto.UserDTO;
import com.sab.payment_service.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
    
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder  getPaymentOrderById(Long id);

    PaymentOrder getPaymentOrderByPaymentId(String id);

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException;

    String  createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;
}
