package com.example.online_portfolio_website.model;

import java.util.List;

public class Project {

    private int id;
    private String title;
    private String category;
    private String description;
    private String initials;
    private String projectUrl;
    private int year;
    private String colorA;   // cover gradient start
    private String colorB;   // cover gradient end
    private List<String> tags;

    public Project() {
    }

    public Project(int id, String title, String category, String description, String initials,
                   String projectUrl, int year, String colorA, String colorB, List<String> tags) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.initials = initials;
        this.projectUrl = projectUrl;
        this.year = year;
        this.colorA = colorA;
        this.colorB = colorB;
        this.tags = tags;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInitials() { return initials; }
    public void setInitials(String initials) { this.initials = initials; }

    public String getProjectUrl() { return projectUrl; }
    public void setProjectUrl(String projectUrl) { this.projectUrl = projectUrl; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getColorA() { return colorA; }
    public void setColorA(String colorA) { this.colorA = colorA; }

    public String getColorB() { return colorB; }
    public void setColorB(String colorB) { this.colorB = colorB; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}