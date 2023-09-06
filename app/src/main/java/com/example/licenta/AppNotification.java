package com.example.licenta;

public class AppNotification {
    private String title;
    private String description;
    private String category;
    private String url;
    private String imageUrl;

    public AppNotification(String title, String description, String category) {
        this.title = title;
        this.description= description;
        this.category =category;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public String getUrl() {
        return url;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
