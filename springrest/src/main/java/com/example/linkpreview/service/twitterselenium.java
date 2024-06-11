package com.example.linkpreview.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.linkpreview.model.LinkPreviewResponse;

import demo.MetaTagContentGetter;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class twitterselenium {
    public static LinkPreviewResponse fetch(String urlString, WebDriver driver) {
        try {
            URL url = new URL(urlString);
            String domain = url.getHost();

            driver.get(urlString);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

            String title = MetaTagContentGetter.getMetaTagContent(driver, wait, "twitter:title");
            if (title == null || title.isEmpty()) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:title");
            }
            String description = MetaTagContentGetter.getMetaTagContent(driver, wait, "twitter:description");
            if (description == null || description.isEmpty()) {
                description = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:description");
                if (description == null || description.isEmpty()) {
                    description = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "description");
                }
            }
            String imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "twitter:image");
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "twitter:image:src");
                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:image");
                }
            }
            
            return new LinkPreviewResponse(title, description, imageUrl, domain);
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            
        }
    }
}
