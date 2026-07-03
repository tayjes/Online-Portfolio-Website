package com.example.online_portfolio_website.model;

public class Project {

    private int id;
    private String title;
    private String category;
    private String description;
    private String initials;   // used by the frontend to render a placeholder thumbnail
    private String projectUrl;

    public Project() {
    }

    public Project(int id, String title, String category, String description, String initials, String projectUrl) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.initials = initials;
        this.projectUrl = projectUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }
}