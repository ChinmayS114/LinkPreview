package com.example.linkpreview.service;

import org.openqa.selenium.WebDriver;

import com.example.linkpreview.model.LinkPreviewResponse;

import demo.HTML;
import demo.OpenGraphTagGetter;
import demo.PreviewPrinter;
import demo.imageUtils;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class LinkedInPreview {

    public static LinkPreviewResponse fetch(String urlString, WebDriver driver, long startTimeNano, List<String> additionalInfo) {
        try {
            String pageSource = PageSourceFetcher.fetchPageSource(urlString);
           
            
            long fetchStartTimeNano = System.nanoTime();

            if (pageSource != null) {
                String title = OpenGraphTagGetter.getTitle(pageSource);
                String description = OpenGraphTagGetter.getDescription(pageSource);
                String imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);

             
                if (title != null && !title.isEmpty() && description != null && !description.isEmpty() &&
                    imageUrl != null && !imageUrl.isEmpty() && !imageUtils.isImageTooSmall(driver, imageUrl, 120, 120)) {

                    PreviewPrinter.printPreview(title, description, imageUrl, urlString);

                   
                    long elapsedTimeNano = System.nanoTime() - fetchStartTimeNano;
                    double elapsedTimeSeconds = (double) elapsedTimeNano / 1_000_000_000.0;

                    
                    String info = "LinkedIn Response: Elapsed Time (static) - " + elapsedTimeSeconds + " seconds";
                    additionalInfo.add(info);
                    URL url = new URL(urlString);
                    String domain = url.getHost();
                    String u = urlString;
                    return new LinkPreviewResponse(title, description, imageUrl, domain);
                }
            }

           
            return FBandLiSele.fetchUsingSelenium(urlString, pageSource, driver, 200, 199, startTimeNano, additionalInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}