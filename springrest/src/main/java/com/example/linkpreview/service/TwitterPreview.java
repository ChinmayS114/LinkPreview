package com.example.linkpreview.service;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.example.linkpreview.model.LinkPreviewResponse;

import demo.HTML;
import demo.OpenGraphTagGetter;
import demo.TwitterSelenium;
import demo.TwitterTagGetter;

public class TwitterPreview {

    public static LinkPreviewResponse fetch(String url, WebDriver driver) {
        try {
            String pageSource = HTML.fetchPageSource(url);
            if (pageSource != null) {
                String title = TwitterTagGetter.getTitle(pageSource);
                String description = TwitterTagGetter.getDescription(pageSource);
                String imageUrl = TwitterTagGetter.getImageUrl(pageSource);

                if (title == null || description == null || imageUrl == null) {
                    title = OpenGraphTagGetter.getTitle(pageSource);
                    description = OpenGraphTagGetter.getDescription(pageSource);
                    imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);
                }

                if (title != null && !title.isEmpty() && description != null && !description.isEmpty() && imageUrl != null && !imageUrl.isEmpty()) {
                	URL url1 = new URL(url);
                	String domain = url1.getHost();
                	return new LinkPreviewResponse(title, description, imageUrl, domain);
                }
            }

            return twitterselenium.fetch(url, driver);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
