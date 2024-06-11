package com.example.linkpreview.service;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.example.linkpreview.model.LinkPreviewResponse;

import demo.FbAndLiSelenium;
import demo.HTML;
import demo.OpenGraphTagGetter;
import demo.imageUtils;

public class LinkedInPreview {

    public static LinkPreviewResponse fetch(String urlString, WebDriver driver) {
        try {
            String pageSource = HTML.fetchPageSource(urlString);
            if (pageSource != null) {
                String title = OpenGraphTagGetter.getTitle(pageSource);
                String description = OpenGraphTagGetter.getDescription(pageSource);
                String imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);

                if (!title.isEmpty() && title != null && description != null &&
                   (!description.isEmpty() && imageUrl != null && !imageUrl.isEmpty() && !imageUtils.isImageTooSmall(imageUrl, 120, 120))) {
                	URL url = new URL(urlString);
                	String domain = url.getHost();
                       return new LinkPreviewResponse(title, description, imageUrl, domain);
                }
            }

            return FBandLiSele.fetchUsingSelenium(urlString, driver, 120, 120);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
