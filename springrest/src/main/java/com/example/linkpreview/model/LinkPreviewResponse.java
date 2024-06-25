package com.example.linkpreview.model;

public class LinkPreviewResponse {
    private String title;
    private String description;
    private String imageUrl;
    private String domain;



    public LinkPreviewResponse() {
    }

    public LinkPreviewResponse(String title, String description, String imageUrl, String domain) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.domain = domain;
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
}