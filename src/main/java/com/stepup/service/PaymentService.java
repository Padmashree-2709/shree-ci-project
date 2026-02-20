package com.stepup.service;

import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // You should put these in application.properties
    // For now, hardcoding or using placeholders.
    // Replace with your Actual Keys for testing
    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public String createOrder(double amount) throws Exception {
    System.out.println("Creating Razorpay order for amount: " + amount);
    System.out.println("KeyId: " + keyId);

    RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

    JSONObject orderRequest = new JSONObject();
    orderRequest.put("amount", (int) (amount * 100));
    orderRequest.put("currency", "INR");
    orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

    com.razorpay.Order order = razorpay.orders.create(orderRequest);

    System.out.println("Order created: " + order.toString());
    return order.get("id");
}

}
