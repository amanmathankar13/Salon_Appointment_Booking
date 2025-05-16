package com.sab.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.sab.payment_service.domain.PaymentMethod;
import com.sab.payment_service.entity.PaymentOrder;
import com.sab.payment_service.payload.dto.BookingDTO;
import com.sab.payment_service.payload.dto.UserDTO;
import com.sab.payment_service.payload.response.PaymentLinkResponse;
import com.sab.payment_service.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO bookingDTO, @RequestParam PaymentMethod paymentMethod) throws RazorpayException, StripeException
    {
        UserDTO user = new UserDTO();
        user.setName("Aman");
        user.setEmail("aman@gmail.com");
        user.setId(1L);

        PaymentLinkResponse response = paymentService.createOrder(user, bookingDTO, paymentMethod);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/get/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) {
        return ResponseEntity.ok(paymentService.getPaymentOrderById(paymentOrderId));
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(@RequestParam String paymentId, @RequestParam String paymentLinkId) throws RazorpayException {
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);
        Boolean res = paymentService.proceedPayment(paymentOrder, paymentId, paymentLinkId);
        return ResponseEntity.ok(res);
    }
    
}
