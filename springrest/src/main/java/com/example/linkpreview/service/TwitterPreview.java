package com.example.linkpreview.service;

import org.openqa.selenium.WebDriver;
//import com.example.linkpreview.model.TwitterPreviewResponse;
import demo.OpenGraphTagGetter;
import demo.PreviewPrinter;
import demo.TwitterTagGetter;
import demo.imageUtils;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class TwitterPreview {

    public static TwitterPreviewResponse fetch(String url, WebDriver driver, long startTimeNano, List<String> additionalInfo) {
        try {
         
            String pageSource = PageSourceFetcher.fetchPageSource(url);
            long fetchStartTimeNano = System.nanoTime();
            if (pageSource != null) {
                String title = TwitterTagGetter.getTitle(pageSource);
                String description = TwitterTagGetter.getDescription(pageSource);
                String imageUrl = TwitterTagGetter.getImageUrl(pageSource);
                String twitterCard = TwitterTagGetter.getCard(pageSource);
               
                if (twitterCard == null || twitterCard.isEmpty()) {
                    twitterCard = "default";
                }
                
                if(title == null)
                {
                	title = OpenGraphTagGetter.getTitle(pageSource);
                }
                if(description == null)
                {
                	description = OpenGraphTagGetter.getDescription(pageSource);
                }
                if(imageUrl == null)
                {
                	imageUrl = OpenGraphTagGetter.getImageUrlLast(pageSource);
                }
                
                
                if (title != null && !title.isEmpty() && description != null && !description.isEmpty() && imageUrl != null && !imageUrl.isEmpty()) {
                    PreviewPrinter.printPreview(title, description, imageUrl, url);

                    
                    URL url1 = new URL(url);
                    String domain = url1.getHost();
                    long elapsedTimeNano = System.nanoTime() - fetchStartTimeNano;
                    double elapsedTimeSeconds = (double) elapsedTimeNano / 1_000_000_000.0;

                    
                    String info = "Twitter Response: Elapsed Time (static)- " + elapsedTimeSeconds + " seconds";
                    additionalInfo.add(info);

                    return new TwitterPreviewResponse(title, description, imageUrl, domain, twitterCard);
                }
            }

           
            return twitterselenium.fetch(url, pageSource, driver, startTimeNano, additionalInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
