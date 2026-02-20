package com.stepup.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendWhatsApp(String phoneNumber, String message) {
        // In a real application, you would integrate with Twilio or Meta Business API here.
        // For this demo, we will simply log the notification to the console.
        System.out.println("=================================================");
        System.out.println("[WHATSAPP NOTIFICATION]");
        System.out.println("To: " + phoneNumber);
        System.out.println("Message: " + message);
        System.out.println("=================================================");
    }
}
