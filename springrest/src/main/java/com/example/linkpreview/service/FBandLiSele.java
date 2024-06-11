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
import demo.PreviewPrinter;
import demo.imageUtils;

public class FBandLiSele {

    public static LinkPreviewResponse fetchUsingSelenium(String urlString, WebDriver driver, int minW, int minH) {
        try {
//        driver.get(urlString);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//            System.out.println("J");
            String title = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:title");
            if (title == null || title.isEmpty()) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "title");
            }
            if (title == null || title.isEmpty()) {
                title = getFirstNonNullTitle(driver);
            }
            String description = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:description");
            if (description == null || description.isEmpty()) {
                description = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "description");
            }
            String imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:image");
            
            if (imageUrl == null || imageUrl.isEmpty()) {
            	imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "image");
            }
            
            if (imageUrl == null || imageUtils.isImageTooSmall(imageUrl, minW, minH)) {
                imageUrl = imageUtils.findFirstLargeImage(driver, minW, minH);
            }
//            PreviewPrinter.printPreview(title, description, imageUrl, urlString, "LinkedIn");
            URL url = new URL(urlString);
        	String domain = url.getHost();
            return new LinkPreviewResponse(title, description, imageUrl, domain);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
//            driver.quit();
        }
    }

    private static String getFirstNonNullTitle(WebDriver driver) {
        try {
            // Try to get the title from the <title> tag
            WebElement titleTag = driver.findElement(By.tagName("title"));
            if (titleTag != null && !titleTag.getText().isEmpty()) {
                return titleTag.getText();
            }
           
            // Try to get the title from the first <h1> tag
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
