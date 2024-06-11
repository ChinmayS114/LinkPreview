package demo;


import org.openqa.selenium.WebDriver;


public class facebookNew {

public static void preview(String urlString, WebDriver driver) {
   try {
       
       String pageSource = HTML.fetchPageSource(urlString);
       if (pageSource != null) {
           
           String title = OpenGraphTagGetter.getTitle(pageSource);
           String description = OpenGraphTagGetter.getDescription(pageSource);
           String imageUrl = OpenGraphTagGetter.getImageUrl(pageSource);

           
           if (imageUrl != null && !imageUtils.isImageTooSmall(imageUrl, 200, 200)) {
             
               PreviewPrinter.printPreview(title, description, imageUrl, urlString, "Facebook");
               return;
           }
       }

       
       FbAndLiSelenium.fetchUsingSelenium(urlString, driver, 200, 200);
   } catch (Exception e) {
       e.printStackTrace();
   }
}
}
