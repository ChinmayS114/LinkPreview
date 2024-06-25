package com.example.linkpreview.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PageSourceFetcher {

    private static boolean isPageSourceFetched = false;
    private static String pageSource = null;
    private static int fetchCounter = 0;

    public static synchronized String fetchPageSource(String urlString) {
        fetchCounter++; 

        	
        if (fetchCounter % 3 == 1) {
            StringBuilder pageSourceBuilder = new StringBuilder();

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(4000);
                connection.setReadTimeout(4000);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    pageSourceBuilder.append(inputLine);
                }
                in.close();

                pageSource = pageSourceBuilder.toString();
                isPageSourceFetched = true;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        
        if (fetchCounter % 3 == 0) {
            String temp = pageSource;
            reset();
            return temp;
        }

        return pageSource;
    }

    public static synchronized void reset() {
        isPageSourceFetched = false;
        pageSource = null;
    }
}



