package demo;


import org.openqa.selenium.WebDriver;


public class LinkedInNew {

public static void preview(String urlString, WebDriver driver) {
   try {
       
       String pageSource = HTML.fetchPageSource(urlString);
       if (pageSource != null) {
           
           String title = OpenGraphTagGetter.getTitle(pageSource);
           String description = OpenGraphTagGetter.getDescription(pageSource);
           String imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);

         
           if (imageUrl != null && !imageUtils.isImageTooSmall(imageUrl, 120, 120)) {
               
               PreviewPrinter.printPreview(title, description, imageUrl, urlString, "LinkedIn");
               return;
           }
       }

       
       FbAndLiSelenium.fetchUsingSelenium(urlString, driver, 120, 120);
   } catch (Exception e) {
       e.printStackTrace();
   }
}
}
