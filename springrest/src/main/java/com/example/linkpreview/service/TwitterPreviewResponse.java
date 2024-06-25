package com.example.linkpreview.service;

public class TwitterPreviewResponse {
    private String title;
    private String description;
    private String imageUrl;
    private String domain;
    private String twitterCard;

   

    public TwitterPreviewResponse() {
    }

    public TwitterPreviewResponse(String title, String description, String imageUrl, String domain, String twitterCard) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.domain = domain;
        this.twitterCard = twitterCard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTwitterCard() {
        return twitterCard;
    }

    public void setTwitterCard(String twitterCard) {
        this.twitterCard = twitterCard;
    }
}