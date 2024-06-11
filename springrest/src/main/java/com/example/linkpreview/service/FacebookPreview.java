package com.example.linkpreview.service;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.example.linkpreview.model.LinkPreviewResponse;

import demo.FbAndLiSelenium;
import demo.HTML;
import demo.OpenGraphTagGetter;
import demo.imageUtils;

public class FacebookPreview {

    public static LinkPreviewResponse fetch(String urlString, WebDriver driver) {
        try {
            String pageSource = HTML.fetchPageSource(urlString);
            if (pageSource != null) {
                String title = OpenGraphTagGetter.getTitle(pageSource);
                String description = OpenGraphTagGetter.getDescription(pageSource);
                String imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);

                if (title != null && !title.isEmpty() && description != null && !description.isEmpty() && imageUrl != null && !imageUrl.isEmpty() && imageUrl != null && !imageUtils.isImageTooSmall(imageUrl, 200, 200)) {
                	URL url = new URL(urlString);
                	String domain = url.getHost();
                	return new LinkPreviewResponse(title, description, imageUrl, domain);
                }
            }

            return FBandLiSele.fetchUsingSelenium(urlString, driver, 200, 200);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
