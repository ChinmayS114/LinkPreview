package com.example.linkpreview.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

//import com.example.linkpreview.model.AggregatedLinkPreviewResponse;
import com.example.linkpreview.model.LinkPreviewResponse;
//import com.example.linkpreview.model.TwitterPreviewResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LinkPreviewService {

    private WebDriver driver;
    private boolean isDriverInitialized = false;
    private final Map<String, AggregatedResponse> cache = new HashMap<>();
    private long startTimeNano; // Nanosecond timer

    public AggregatedResponse getLinkPreview(String url) {
       //
        startTimeNano = System.nanoTime();
        List<String> additionalInfo = new ArrayList<>();
        if (!isDriverInitialized) {
        long driverInitStartTime = System.nanoTime();
            initializeWebDriver();
            isDriverInitialized = true;
            long driverInitEndTime = System.nanoTime();
            double driverInitTimeSeconds = (driverInitEndTime - driverInitStartTime) / 1_000_000_000.0;
            String info = "Driver Intialization time - " + driverInitTimeSeconds + " seconds";
            additionalInfo.add(info);

        }

        try {
           
            TwitterPreviewResponse twitterResponse = TwitterPreview.fetch(url, driver, startTimeNano, additionalInfo);
            LinkPreviewResponse facebookResponse = FacebookPreview.fetch(url, driver, startTimeNano, additionalInfo);
            LinkPreviewResponse linkedinResponse = LinkedInPreview.fetch(url, driver, startTimeNano, additionalInfo);

          
            System.out.println("Additional Info:");
            for (String info : additionalInfo) {
                System.out.println(info);
            }

           
            AggregatedResponse aggregatedResponse = new AggregatedResponse(twitterResponse, facebookResponse, linkedinResponse);

           
            long elapsedTimeNano = System.nanoTime() - startTimeNano;

            
            double elapsedTimeSeconds = (double) elapsedTimeNano / 1_000_000_000.0;
            System.out.println("Elapsed time (seconds): " + elapsedTimeSeconds);

           
            cache.put(url, aggregatedResponse);

            return aggregatedResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch link preview", e);
        }
    }

    private void initializeWebDriver() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--incognito");
        options.addArguments("--lang=en-US");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36");
//        options.addArguments("user-agent=ozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        Options driverOptions = driver.manage();
        driverOptions.deleteAllCookies();
    }
}

