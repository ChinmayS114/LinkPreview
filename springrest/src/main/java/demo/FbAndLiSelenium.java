package demo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FbAndLiSelenium {
public static void fetchUsingSelenium(String urlString, WebDriver driver, int minW, int minH) {
       
        try {
//            URL url = new URL(urlString);
//            String domain = url.getHost();
//
//            driver.get(urlString);
//        	
        	
        	
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            String title = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:title");
            if (title == null || title.isEmpty()) {
                title = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "title");
            }
            String description = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:description");
            if (description == null || description.isEmpty()) {
                description = MetaTagContentGetter.getMetaTagContent(driver, wait, "name", "description");
            }
            String imageUrl = MetaTagContentGetter.getMetaTagContent(driver, wait, "og:image");
         
            if (imageUrl == null || imageUtils.isImageTooSmall(imageUrl,minW,minH)) {
               
                imageUrl = imageUtils.findFirstLargeImage(driver, minW, minH);
            }

           
            if(minW == 200)
            PreviewPrinter.printPreview(title, description, imageUrl, urlString, "Facebook");
            else
            PreviewPrinter.printPreview(title, description, imageUrl, urlString, "LinkedIn");
        }
//        catch (MalformedURLException e) {
//            System.out.println("Invalid URL");
//            e.printStackTrace();
//        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
//            driver.quit();
        }
    }
}