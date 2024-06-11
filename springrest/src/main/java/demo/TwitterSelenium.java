package demo;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class TwitterSelenium {
public static void fetch(String urlString, WebDriver driver) {

        try {
            URL url = new URL(urlString);
            String domain = url.getHost();

            driver.get(urlString);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

            
            
            String title = MetaTagContentGetter.getMetaTagContent(driver, wait, "twitter:title");
            if (title == null || title.isEmpty()) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:title");
            }
            
            
            String description = MetaTagContentGetter.getMetaTagContent(driver, wait, "twitter:description");
            if (description == null || description.isEmpty()) {
                description = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:description");
                if (description == null || description.isEmpty()) {
                    description = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "description");
                }
            }
            
            
            String imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "twitter:image");
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "twitter:image:src");
                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:image");
                }
            }

           
            PreviewPrinter.printPreview(title, description, imageUrl, urlString, "Twitter");
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            driver.quit();
        }
    }
}