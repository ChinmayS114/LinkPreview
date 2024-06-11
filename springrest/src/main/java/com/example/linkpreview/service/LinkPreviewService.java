package com.example.linkpreview.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.example.linkpreview.service.AggregatedResponse;
import com.example.linkpreview.model.LinkPreviewResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class LinkPreviewService {

    private WebDriver driver;
    private boolean isDriverInitialized = false;
    private final Map<String, AggregatedResponse> cache = new HashMap<>();

    public AggregatedResponse getLinkPreview(String url) {
        System.out.println(isDriverInitialized);
       
       
        if (cache.containsKey(url)) {
            return cache.get(url);
        }

       
        if (!isDriverInitialized) {
            initializeWebDriver();
            isDriverInitialized = true;
        }

        try {
     
            LinkPreviewResponse twitterResponse = TwitterPreview.fetch(url, driver);
            LinkPreviewResponse facebookResponse = FacebookPreview.fetch(url, driver);
            LinkPreviewResponse linkedinResponse = LinkedInPreview.fetch(url, driver);

           
            AggregatedResponse aggregatedResponse = new AggregatedResponse(twitterResponse, facebookResponse, linkedinResponse);

       
            cache.put(url, aggregatedResponse);

           
            return aggregatedResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch link preview", e);
        }
    }

    private void initializeWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        driver = new ChromeDriver(options);
    }
}
