package com.example.online_portfolio_website.controller;

import com.example.online_portfolio_website.model.Project;
import com.example.online_portfolio_website.model.ServiceItem;
import com.example.online_portfolio_website.model.Testimonial;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Serves the dynamic content shown on the portfolio site. Data is kept in memory
 * for simplicity; swap for a repository/database layer if persistence is needed.
 */
@RestController
@RequestMapping("/api")
public class PortfolioController {

    private final List<Project> projects = List.of(
            new Project(1, "Aurora Cafe", "Branding",
                    "Logo, packaging and menu system for a boutique cafe chain.", "AC", "#",
                    2026, "#C98A2B", "#5B3A12", List.of("Logo", "Packaging", "Print")),
            new Project(2, "StyleHub", "Web Design",
                    "Responsive storefront redesign focused on conversion and speed.", "SH", "#",
                    2026, "#4C6B58", "#14261F", List.of("E-commerce", "Responsive", "Figma")),
            new Project(3, "FitTrack", "UI/UX",
                    "Fitness tracking app with a clean, data-first interface.", "FT", "#",
                    2025, "#3D5A80", "#101B2B", List.of("Mobile", "Prototype", "Research")),
            new Project(4, "Studio Nine", "Web Design",
                    "Minimal one-page portfolio for a photography studio.", "SN", "#",
                    2025, "#8A6F9E", "#2A1F36", List.of("Portfolio", "Gallery", "Animation")),
            new Project(5, "Verde Organics", "Branding",
                    "Identity and shelf design for an organic grocery start-up.", "VO", "#",
                    2025, "#7BA05B", "#233317", List.of("Identity", "Packaging")),
            new Project(6, "PayNest", "UI/UX",
                    "Dashboard and onboarding flow for a small-business payments tool.", "PN", "#",
                    2024, "#C4543A", "#3A140C", List.of("Dashboard", "Fintech", "Design system"))
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

    @GetMapping("/stats")
    public Map<String, Integer> getStats() {
        return Map.of("projects", projects.size(), "clients", 24, "years", 6);
    }
}