package com.example.online_portfolio_website.controller;

import com.example.online_portfolio_website.model.Project;
import com.example.online_portfolio_website.model.ServiceItem;
import com.example.online_portfolio_website.model.Testimonial;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Serves the dynamic content shown on the portfolio site (projects, services,
 * testimonials). Kept in-memory for simplicity -- swap for a database/repository
 * layer later if the assignment needs persistence.
 */
@RestController
@RequestMapping("/api")
public class PortfolioController {

    private final List<Project> projects = List.of(
            new Project(1, "Brand Identity - Aurora Cafe", "Branding",
                    "Logo, packaging and menu design for a boutique cafe chain.", "AC", "#"),
            new Project(2, "E-Commerce UI - StyleHub", "Web Design",
                    "Responsive storefront redesign focused on conversion.", "SH", "#"),
            new Project(3, "Mobile App - FitTrack", "UI/UX",
                    "Fitness tracking app with a clean, data-first interface.", "FT", "#"),
            new Project(4, "Portfolio Site - Studio Nine", "Web Design",
                    "Minimal one-page portfolio for a photography studio.", "SN", "#")
    );

    private final List<ServiceItem> services = List.of(
            new ServiceItem(1, "Web Design", "Custom, responsive websites built around the client's brand.", "🖥️"),
            new ServiceItem(2, "Branding", "Logo design, colour systems and visual identity guidelines.", "🎨"),
            new ServiceItem(3, "UI/UX Design", "Wireframes, prototypes and user-centred interface design.", "📐"),
            new ServiceItem(4, "Maintenance", "Ongoing content updates and performance improvements.", "🛠️")
    );

    private final List<Testimonial> testimonials = List.of(
            new Testimonial(1, "Ananya Rao", "Founder, Aurora Cafe",
                    "The team captured our brand perfectly and delivered ahead of schedule.", 5),
            new Testimonial(2, "Marcus Lee", "Product Lead, StyleHub",
                    "Our conversion rate improved noticeably after the redesign.", 5),
            new Testimonial(3, "Priya Sharma", "Owner, Studio Nine",
                    "Clean design, clear communication, and great attention to detail.", 4)
    );

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return projects;
    }

    @GetMapping("/services")
    public List<ServiceItem> getServices() {
        return services;
    }

    @GetMapping("/testimonials")
    public List<Testimonial> getTestimonials() {
        return testimonials;
    }
}