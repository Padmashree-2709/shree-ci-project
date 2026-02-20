package com.stepup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private com.stepup.service.ChatRuleService chatRuleService;

    @Autowired
    private com.stepup.service.OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> getResponse(@RequestBody Map<String, String> payload, jakarta.servlet.http.HttpSession session) {
        String userMessage = payload.getOrDefault("message", "").toLowerCase();
        Map<String, Object> responseMap = new HashMap<>();
        com.stepup.model.User user = (com.stepup.model.User) session.getAttribute("user");
        
        List<com.stepup.model.ChatRule> rules = chatRuleService.getAllRules();
        com.stepup.model.ChatRule matchedRule = null;

        for (com.stepup.model.ChatRule rule : rules) {
            String[] keywords = rule.getKeywords().toLowerCase().split(",");
            for (String keyword : keywords) {
                if (userMessage.contains(keyword.trim())) {
                    matchedRule = rule;
                    break;
                }
            }
            if (matchedRule != null) break;
        }

        String botMessage;
        List<String> suggestionList = new ArrayList<>();

        // Order-Aware Logic
        if (user != null && matchedRule != null && (matchedRule.getKeywords().contains("order") || matchedRule.getKeywords().contains("track") || matchedRule.getKeywords().contains("status"))) {
            List<com.stepup.model.Order> userOrders = orderService.getOrdersByUser(user);
            if (!userOrders.isEmpty()) {
                com.stepup.model.Order lastOrder = userOrders.get(0);
                botMessage = "I found your latest order #" + lastOrder.getId() + " placed on " + 
                             lastOrder.getOrderDate().toLocalDate() + ". The current status is: " + lastOrder.getStatus() + 
                             ". Your tracking number is " + lastOrder.getTrackingNumber() + ".";
                suggestionList.add("Track Order #" + lastOrder.getId());
                suggestionList.add("My Orders");
            } else {
                botMessage = matchedRule.getResponse();
            }
        } else if (matchedRule != null) {
            botMessage = matchedRule.getResponse();
            if (matchedRule.getSuggestions() != null && !matchedRule.getSuggestions().isEmpty()) {
                String[] sugs = matchedRule.getSuggestions().split(",");
                for (String s : sugs) suggestionList.add(s.trim());
            }
        } else {
            botMessage = "I'm not sure I understand. Could you try rephrasing? I can help with orders, payments, sizing, and returns!";
            suggestionList.add("Track Order");
            suggestionList.add("Payment Help");
            suggestionList.add("Contact Support");
        }

        responseMap.put("message", botMessage);
        responseMap.put("suggestions", suggestionList);
        responseMap.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(responseMap);
    }
}
