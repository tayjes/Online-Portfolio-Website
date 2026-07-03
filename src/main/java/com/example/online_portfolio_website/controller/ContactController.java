package com.example.online_portfolio_website.controller;

import com.example.online_portfolio_website.model.ContactMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Handles the "Contact Us" form on the frontend. For this assignment the
 * message is simply logged; wire up email sending or a database if needed.
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @PostMapping
    public Map<String, Object> submitContact(@RequestBody ContactMessage contactMessage) {
        log.info("New contact message from {} <{}>: {}",
                contactMessage.getName(), contactMessage.getEmail(), contactMessage.getMessage());

        return Map.of(
                "status", "success",
                "message", "Thanks " + contactMessage.getName() + ", your message has been received!"
        );
    }
}