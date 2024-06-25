package com.example.linkpreview.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;



import demo.MetaTagContentGetter;
import demo.OpenGraphTagGetter;
import demo.PreviewPrinter;
import demo.imageUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class twitterselenium {
    public static TwitterPreviewResponse fetch(String urlString, String pageSource, WebDriver driver, long startTimeNano, List<String> additionalInfo) {
        try {
            URL url = new URL(urlString);
            String domain = url.getHost();
            String u = urlString;
            driver.get(u);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

            
            long fetchStartTimeNano = System.nanoTime();

            String title = MetaTagContentGetter.getMetaTagContent(driver, wait, "twitter:title");
            if (title == null || title.isEmpty()) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:title");
            }
            if (title == null || title.isEmpty()) {
                title = MetaTagContentGetter.getTitleContent(driver, wait);
            }
            if (title == null || title.isEmpty() && pageSource != null) {
                title = OpenGraphTagGetter.getTitle(pageSource);
            }

            String description = MetaTagContentGetter.getMetaTagContent(driver, wait, "twitter:description");
            if (description == null || description.isEmpty()) {
                description = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:description");
                if (description == null || description.isEmpty()) {
                    description = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "description");
                }
            }
            if (description == null || description.isEmpty() && pageSource != null) {
                description = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "description");
            }
            if (description == null || description.isEmpty()) {
                description = OpenGraphTagGetter.getDescription(pageSource);
            }
            String imageUrl = null;
            if(pageSource != null)
            {
            	imageUrl = OpenGraphTagGetter.getImageUrlLast(pageSource);
            }
            if(imageUrl == null) {
            	imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "twitter:image");
            }
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "twitter:image:src");
                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = MetaTagContentGetter.getLastMetaTagContent(driver, wait, "property", "og:image");
                }
            }

            String twitterCard = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "twitter:card");
            
            if (twitterCard == null || twitterCard.isEmpty()) {
                twitterCard = "default";
            }
           
            
            long elapsedTimeNano = System.nanoTime() - fetchStartTimeNano;
            double elapsedTimeSeconds = (double) elapsedTimeNano / 1_000_000_000.0;

           
            String info = "Twitter Response: Elapsed Time (dynamic) - " + elapsedTimeSeconds + " seconds";
            additionalInfo.add(info);
            
//            PreviewPrinter.printPreview(title, description, imageUrl, domain);
            return new TwitterPreviewResponse(title, description, imageUrl, domain, twitterCard);
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