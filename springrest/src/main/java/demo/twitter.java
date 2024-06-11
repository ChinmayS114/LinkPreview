package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class twitter{

    public static void preview(String urlString) {
        try {
        	
            //HTTP
            String pageSource = fetchPageSource(urlString);
            
//            System.out.println(pageSource);
            
            if (pageSource != null) {
//             System.out.println("IN");
            	
            	
                //Twitter and OG cards
                String title = getMetaTagContent(pageSource, "twitter:title");
                if (title == null || title.isEmpty()) {
                    title = getMetaTagContent(pageSource, "og:title");
                }
                
                String description = getMetaTagContent(pageSource, "twitter:description");
                if (description == null || description.isEmpty()) {
                    description = getMetaTagContent(pageSource, "og:description");
                    if (description == null || description.isEmpty()) {
                        description = getMetaTagContent(pageSource, "description");
                    }
                }
                
                String imageUrl = getMetaTagContent(pageSource, "name", "twitter:image");
                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = getMetaTagContent(pageSource, "name", "twitter:image:src");
                    if (imageUrl == null || imageUrl.isEmpty()) {
                        imageUrl = getMetaTagContent(pageSource, "og:image");
                    }
                }

                
                if (title != null  && !title.isEmpty() &&  description != null && !description.isEmpty()  && imageUrl != null && !imageUrl.isEmpty() ) {
                    printPreview(title, description, imageUrl, urlString);
                    return;
                }
            }

            //selenium
            fetchUsingSelenium(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fetchPageSource(String urlString) {
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

    private static void fetchUsingSelenium(String urlString) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless");     //going headless
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

            String title = getMetaTagContent(driver, wait, "twitter:title");
            if (title == null || title.isEmpty()) {
                title = getMetaTagContent(driver, wait, "og:title");
            }
            
            
            String description = getMetaTagContent(driver, wait, "twitter:description");
            if (description == null || description.isEmpty()) {
                description = getMetaTagContent(driver, wait, "og:description");
                if (description == null || description.isEmpty()) {
                    description = getMetaTagContent(driver, wait, "name", "description");
                }
            }
            
            
            String imageUrl = getMetaTagContent(driver, wait, "name", "twitter:image");
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = getMetaTagContent(driver, wait, "name", "twitter:image:src");
                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = getMetaTagContent(driver, wait, "og:image");
                }
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

    
    
    private static String getMetaTagContent(String pageSource, String property) {
        Document doc = Jsoup.parse(pageSource);
        String content = doc.select("meta[property=" + property + "]").attr("content");
        if (content.isEmpty()) {
            content = doc.select("meta[name=" + property + "]").attr("content");
        }
        return content;
    }

    
    
    private static String getMetaTagContent(WebDriver driver, WebDriverWait wait, String property) {
        try {
            WebElement metaTag = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//meta[@property='" + property + "'] | //meta[@name='" + property + "']")));
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
   
    
    
    private static String getMetaTagContent(String pageSource, String attribute, String value) {
        Document doc = Jsoup.parse(pageSource);
        String content = doc.select("meta[" + attribute + "=" + value + "]").attr("content");
        return content;
    }
   
    
    
    private static String getMetaTagContent(WebDriver driver, WebDriverWait wait, String attribute, String value) {
        try {
            WebElement metaTag = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//meta[@" + attribute + "='" + value + "']")));
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
   
    
    
    private static void printPreview(String title, String description, String imageUrl, String urlString) {
        try {
            URL url = new URL(urlString);
            String domain = url.getHost();
            System.out.println("Preview for Twitter:");
            System.out.println("Domain: " + domain);
            System.out.println("Title: " + (title != null ? title : "Not available"));
            System.out.println("Description: " + (description != null ? description : "Not available"));
            System.out.println("Image URL: " + (imageUrl != null ? imageUrl : "Not available"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
