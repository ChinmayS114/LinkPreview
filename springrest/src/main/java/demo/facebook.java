package demo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class facebook{

    public static void preview(String urlString){
        try {
            //HTTP
            String pageSource = fetchPageSource(urlString);
//            System.out.println(pageSource);
            if (pageSource != null) {
                
//             System.out.println("IN");
                String title = getMetaTagContent(pageSource, "og:title");
                if (title == null || title.isEmpty()) {
                    title = getMetaTagContent(pageSource, "title", "name");
                }
                String description = getMetaTagContent(pageSource, "og:description");
                if (description == null || description.isEmpty()) {
                    description = getMetaTagContent(pageSource, "description", "name");
                }
                String imageUrl = getMetaTagContent(pageSource, "og:image");

                
                if (imageUrl != null && !isImageTooSmall(imageUrl)) {
                    
                    printPreview(title, description, imageUrl, urlString);
                    return;
                }
            }

            //Selenium
            fetchUsingSelenium(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fetchPageSource(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            
            return null;
        }
    }

    private static void fetchUsingSelenium(String urlString){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments(
            "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);
        
        driver.manage().window().minimize();
        try {
            URL url = new URL(urlString);
            String domain = url.getHost();

            driver.get(urlString);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            String title = getMetaTagContent(driver, wait, "og:title");
            if (title == null || title.isEmpty()) {
                title = getMetaTagContent(driver, wait, "title", "name");
            }
            String description = getMetaTagContent(driver, wait, "og:description");
            if (description == null || description.isEmpty()) {
                description = getMetaTagContent(driver, wait, "description", "name");
            }
            String imageUrl = getMetaTagContent(driver, wait, "og:image");

            if (imageUrl == null || isImageTooSmall(imageUrl)) {
                
                imageUrl = findFirstLargeImage(driver, 200, 200);
            }

            
            printPreview(title, description, imageUrl, urlString);
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static String getMetaTagContent(String pageSource, String property){
        return getMetaTagContent(pageSource, property, "property");
    }

    
    
    
    private static String getMetaTagContent(String pageSource, String attribute, String attributeType){
        Document doc = Jsoup.parse(pageSource);
        String content = doc.select("meta[" + attributeType + "=" + attribute + "]").attr("content");
        if (content.isEmpty()) {
            content = doc.select("meta[name=" + attribute + "]").attr("content");
        }
        return content;
    }

    
    
    
    private static String getMetaTagContent(WebDriver driver, WebDriverWait wait, String property) {
        return getMetaTagContent(driver, wait, property, "property");
    }

    private static String getMetaTagContent(WebDriver driver, WebDriverWait wait, String attribute, String attributeType) {
        try {
            WebElement metaTag = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//meta[@" + attributeType + "='" + attribute + "'] | //meta[@name='" + attribute + "']")));
            if (metaTag == null) {
                return null;
            }
            String content = metaTag.getAttribute("content");
            if (content == null || content.isEmpty()) {
                content = metaTag.getAttribute("value");
            }
            return content;
        } catch (Exception e) {
            return null;
        }
    }

    
    
    private static boolean isImageTooSmall(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            return image.getWidth() < 200 || image.getHeight() < 200;
        } catch (Exception e) {
            return true;
        }
    }

    private static String findFirstLargeImage(WebDriver driver, int minWidth, int minHeight) {
        try {
        	
        	
//        	WebElement img1 = driver.findElement(By.tagName("img"));
        	
        	
            for (WebElement img : driver.findElements(By.tagName("img"))) {
                String src = img.getAttribute("src");
//                System.out.println(src);
                if (src != null && !src.isEmpty()) {
                    try {
                        URL imgUrl = new URL(src);
                        BufferedImage image = ImageIO.read(imgUrl);
//                        Thread.sleep(10);
//                        System.out.println(src + "   " + image.getHeight() + "   " + image.getWidth());
                        if (image != null && image.getWidth() >= minWidth && image.getHeight() >= minHeight) {
//                            System.out.println("image : " + src);
                        	
                        	
                            return src;
                        }
                    } catch (Exception e) {
                    	
                    	
//                        System.out.println("error image : " + src);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    private static void printPreview(String title, String description, String imageUrl, String urlString) {
        try {
            URL url = new URL(urlString);
            String domain = url.getHost();
            System.out.println("Preview for Facebook:");
            System.out.println("Domain: " + domain);
            System.out.println("Title: " + (title != null ? title : "Not available"));
            System.out.println("Description: " + (description != null ? description : "Not available"));
            System.out.println("Image URL: " + (imageUrl != null ? imageUrl : "Not available"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
