package com.example.online_portfolio_website.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CI/CD DEMO FEATURE
 * Add this file -> commit -> push -> pipeline redeploys -> a banner appears on the site.
 * Delete this file -> commit -> push -> pipeline redeploys -> the banner disappears.
 */
@RestController
public class AnnouncementController {

    @GetMapping("/api/announcement")
    public Map<String, String> announcement() {
        return Map.of("message", "🚀 New: now booking portfolio projects for August — deployed via Jenkins CI/CD");
    }
}