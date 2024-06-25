package com.example.linkpreview.service;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.linkpreview.model.LinkPreviewResponse;

import demo.MetaTagContentGetter;
import demo.OpenGraphTagGetter;
import demo.PreviewPrinter;
import demo.TwitterTagGetter;
import demo.imageUtils;

public class FBandLiSele {

    public static LinkPreviewResponse fetchUsingSelenium(String urlString, String pageSource, WebDriver driver, int minW, int minH, long startTimeNano, List<String> additionalInfo) {
        try {
            
            long fetchStartTimeNano = System.nanoTime();

            driver.get(urlString);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            String title = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:title");
            if (title == null || title.isEmpty()) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "title");
            }
            if (title == null || title.isEmpty()) {
                title = getFirstNonNullTitle(driver);
            }
            if (title == null || title.isEmpty() && pageSource != null) {
                title = OpenGraphTagGetter.getTitle(pageSource);
            }
            if (title == null || title.isEmpty() || title.length() < 3) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "title");
            }
            if (title == null || title.isEmpty()  || title.length() < 3) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:title");
            }
            if (title == null || title.isEmpty() || title.length() < 3) {
                title = MetaTagContentGetter.getTitleContent(driver, wait);
            }
            if (title.length() < 3 || title == null || title.isEmpty() && pageSource != null) {
            	title = TwitterTagGetter.getTitle(pageSource);
            }
            String description = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:description");
            if (description == null || description.isEmpty()) {
                description = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "description");
            }
            if (description == null || description.isEmpty() && pageSource != null) {
                description = OpenGraphTagGetter.getDescription(pageSource);
            }
            String imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:image");
           
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "image");
            }
           
            if (imageUrl != null) {
                if (minH == 200 && imageUtils.isImageTooSmall(driver, imageUrl, minW, minH)) {
                    imageUrl = imageUtils.findFirstLargeImage(driver, minW, minH);
                }
            } else {
                imageUrl = imageUtils.findFirstLargeImage(driver, minW, minH);
            }
           
            if (imageUrl == null || imageUrl.length() < 3) {
                driver.get(urlString);
                imageUrl = imageUtils.findFirstLargeImageFallback(driver, minW, minH);
            }

            URL url = new URL(urlString);
            String domain = url.getHost();

           
            long elapsedTimeNano = System.nanoTime() - fetchStartTimeNano;
            double elapsedTimeSeconds = (double) elapsedTimeNano / 1_000_000_000.0;

            
            if(minH == 200) {
            String info = "Facebook Response: Elapsed Time (dynamic) - " + elapsedTimeSeconds + " seconds";
            additionalInfo.add(info);
            }
            else
            {
            String info = "LinkedIn Response: Elapsed Time (dynamic) - " + elapsedTimeSeconds + " seconds";
            additionalInfo.add(info);
            }

            PreviewPrinter.printPreview(title, description, imageUrl, urlString);
            return new LinkPreviewResponse(title, description, imageUrl, domain);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String findFirstLargeOgImage(WebDriver driver, WebDriverWait wait, int minW, int minH) {
        try {
            List<WebElement> metaTags = driver.findElements(By.xpath("//meta[@property='og:image']"));
            for (WebElement metaTag : metaTags) {
                String imageUrl = metaTag.getAttribute("content");
                if (imageUrl != null && !imageUrl.isEmpty() && !imageUtils.isImageTooSmall(driver, imageUrl, minW, minH)) {
                    return imageUrl;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFirstNonNullTitle(WebDriver driver) {
        try {
           
            WebElement titleTag = driver.findElement(By.tagName("title"));
            if (titleTag != null && !titleTag.getText().isEmpty()) {
                return titleTag.getText();
            }
           
            
            List<WebElement> h1Tags = driver.findElements(By.tagName("h1"));
            for (WebElement h1Tag : h1Tags) {
                if (!h1Tag.getText().isEmpty()) {
                    return h1Tag.getText();
                }
            }
           
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}