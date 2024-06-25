package com.example.linkpreview.service;

import org.openqa.selenium.WebDriver;

import com.example.linkpreview.model.LinkPreviewResponse;


import demo.HTML;
import demo.OpenGraphTagGetter;
import demo.PreviewPrinter;
import demo.imageUtils;

import java.net.URL;
import java.util.List;

public class FacebookPreview {

    public static LinkPreviewResponse fetch(String urlString, WebDriver driver, long startTimeNano, List<String> additionalInfo) {
        try {
            String pageSource = PageSourceFetcher.fetchPageSource(urlString);
            if (pageSource != null) {
                
                long fetchStartTimeNano = System.nanoTime();

                String title = OpenGraphTagGetter.getTitle(pageSource);
                String description = OpenGraphTagGetter.getDescription(pageSource);
                String imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);

                
                long elapsedTimeNano = System.nanoTime() - fetchStartTimeNano;
                double elapsedTimeSeconds = (double) elapsedTimeNano / 1_000_000_000.0;

                
             

               
                if (title != null && !title.isEmpty() && description != null && !description.isEmpty() && imageUrl != null && !imageUrl.isEmpty() && !imageUtils.isImageTooSmall(driver, imageUrl, 200, 200)) {
                    PreviewPrinter.printPreview(title, description, imageUrl, urlString);
                    String info = "Facebook Response: Elapsed Time (static) - " + elapsedTimeSeconds + " seconds";
                    additionalInfo.add(info);
                    URL url = new URL(urlString);
                    String domain = url.getHost();
                    String u = urlString;
                    return new LinkPreviewResponse(title, description, imageUrl, domain);
                }
            }

           
            return FBandLiSele.fetchUsingSelenium(urlString, pageSource, driver, 200, 200,startTimeNano,additionalInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}