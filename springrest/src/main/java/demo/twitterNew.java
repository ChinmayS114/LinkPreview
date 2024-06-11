package demo;

import org.openqa.selenium.WebDriver;


public class twitterNew {

public static void preview(String urlString, WebDriver driver) {
   try {
       
       String pageSource = HTML.fetchPageSource(urlString);
       if (pageSource != null) {
         
           String title = TwitterTagGetter.getTitle(pageSource);
           String description = TwitterTagGetter.getDescription(pageSource);
           String imageUrl = TwitterTagGetter.getImageUrl(pageSource);

         
           if (title == null || description == null || imageUrl == null) {
               title = OpenGraphTagGetter.getTitle(pageSource);
               description = OpenGraphTagGetter.getDescription(pageSource);
               imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);
           }

         
           if (title != null && !title.isEmpty() && description != null && !description.isEmpty() && imageUrl != null && !imageUrl.isEmpty()) {
               PreviewPrinter.printPreview(title, description, imageUrl, urlString, "Twitter");
               return;
           }
       }

     
       TwitterSelenium.fetch(urlString, driver);
   } catch (Exception e) {
       e.printStackTrace();
   }
}

    }