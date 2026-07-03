 package com.example.online_portfolio_website.model;

public class Testimonial {

    private int id;
    private String clientName;
    private String clientRole;
    private String message;
    private int rating; // out of 5

    public Testimonial() {
    }

    public Testimonial(int id, String clientName, String clientRole, String message, int rating) {
        this.id = id;
        this.clientName = clientName;
        this.clientRole = clientRole;
        this.message = message;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientRole() {
        return clientRole;
    }

    public void setClientRole(String clientRole) {
        this.clientRole = clientRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}