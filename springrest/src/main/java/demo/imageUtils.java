package demo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;


public class imageUtils {

    public static boolean isImageTooSmall(String imageUrl, int minW, int minH) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            return image.getWidth() < minW || image.getHeight() < minH;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


public static boolean isImageTooSmall(WebDriver driver, String imageUrl, int minW, int minH) {
   try {
       driver.get(imageUrl);
       WebElement imgElement = driver.findElement(By.tagName("img"));
       int width = imgElement.getSize().getWidth();
       int height = imgElement.getSize().getHeight();
       return width < minW || height < minH;
   } catch (Exception e) {
       e.printStackTrace();
       return true;
   }
}

public static String findFirstLargeImageFallback(WebDriver driver, int minWidth, int minHeight) {
       try {
           for (WebElement img : driver.findElements(By.tagName("img"))) {
               String src = img.getAttribute("src");
//                System.out.println(src);
               if (src != null && !src.isEmpty()) {
                   try {
                       URL imgUrl = new URL(src);
                       BufferedImage image = ImageIO.read(imgUrl);
                       if (image != null && image.getWidth() >= minWidth && image.getHeight() >= minHeight) {
//                            System.out.println("large image: " + src);
                           return src;
                       }
                   } catch (Exception e) {
//                        System.out.println("Error image: " + src);
                   }
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }
   

   
   
public static String findFirstLargeImage(WebDriver driver, int minWidth, int minHeight) {
   try {
       List<WebElement> images = driver.findElements(By.tagName("img"));
       for (WebElement img : images) {
           String src = img.getAttribute("src");
           System.out.println(src);
       }
       
       JavascriptExecutor js = (JavascriptExecutor) driver;

       for (WebElement img : images) {
           String src = img.getAttribute("src");
           if (src != null && !src.isEmpty()) {
               try {
                  
                   boolean isVisible = img.isDisplayed();
                   if (isVisible) {
                      
                       js.executeAsyncScript(
                           "var img = arguments[0];" +
                           "var callback = arguments[arguments.length - 1];" +
                           "var timeout = setTimeout(function() {" +
                           "    callback(null);" +
                           "}, 2000);" +
                           "img.onload = function() {" +
                           "    clearTimeout(timeout);" +
                           "    callback({ width: img.naturalWidth, height: img.naturalHeight });" +
                           "};" +
                           "if (img.complete) {" +
                           "    clearTimeout(timeout);" +
                           "    callback({ width: img.naturalWidth, height: img.naturalHeight });" +
                           "}"
                       , img);

                     
                       Object result = js.executeAsyncScript(
                           "var img = arguments[0];" +
                           "var callback = arguments[arguments.length - 1];" +
                           "if (img.complete) {" +
                           "    callback({ width: img.naturalWidth, height: img.naturalHeight });" +
                           "} else {" +
                           "    img.onload = function() {" +
                           "        callback({ width: img.naturalWidth, height: img.naturalHeight });" +
                           "    };" +
                           "}"
                       , img);

                       if (result != null) {
                           Map<String, Long> dimensions = (Map<String, Long>) result;
                           int width = dimensions.get("width").intValue();
                           int height = dimensions.get("height").intValue();
                           System.out.println(src + "   " + height + "  " + width);
                           if (width >= minWidth && height >= minHeight) {
                               return src;
                           }
                       } else {
                           System.out.println("Image not loaded within 2 seconds: " + src);
                       }
                   } else {
                       System.out.println("Image not displayed: " + src);
                   }
               } catch (Exception e) {
                   System.out.println("Failed to retrieve image dimensions for: " + src + ", skipping...");
                   e.printStackTrace();
               }
           }
       }
   } catch (Exception e) {
       e.printStackTrace();
   }
   return null;
}
 

//    public static String findFirstLargeJpgLink(WebDriver driver, int minWidth, int minHeight) {
//        try {
//         
//            List<WebElement> elements = driver.findElements(By.xpath("//*"));
//            for (WebElement element : elements) {
//                String[] attributes = {"href", "src", "data-src", "data-href"};
//                for (String attribute : attributes) {
//                    String url = element.getAttribute(attribute);
//                    if (url != null && url.toLowerCase().contains(".jpg")) {
//                        try {
//                            URL imgUrl = new URL(url);
//                            BufferedImage image = ImageIO.read(imgUrl);
//                            if (image != null && image.getWidth() >= minWidth && image.getHeight() >= minHeight) {
//                                return url;
//                            }
//                        } catch (Exception e) {
//                            System.out.println("Error processing image: " + url);
//                            // Check if the file size is greater than 70 KB
//                            if (isFileSizeGreaterThan(new URL(url), 70 * 1024)) {
//                                return url;
//                            }
//                        }
//                    }
//                }
//            }
//
//       
//            List<WebElement> jsonLdElements = driver.findElements(By.xpath("//script[@type='application/ld+json']"));
//            for (WebElement jsonLdElement : jsonLdElements) {
//                String jsonContent = jsonLdElement.getAttribute("innerHTML");
//                JsonElement jsonElement = JsonParser.parseString(jsonContent);
//                if (jsonElement.isJsonObject()) {
//                    JsonObject jsonObject = jsonElement.getAsJsonObject();
//                    if (jsonObject.has("image")) {
//                        JsonElement imageElement = jsonObject.get("image");
//                        if (imageElement.isJsonObject()) {
//                            JsonObject imageObject = imageElement.getAsJsonObject();
//                            if (imageObject.has("url")) {
//                                String url = imageObject.get("url").getAsString();
//                                if (url.toLowerCase().contains(".jpg")) {
//                                    try {
//                                        URL imgUrl = new URL(url);
//                                        BufferedImage image = ImageIO.read(imgUrl);
//                                        if (image != null && image.getWidth() >= minWidth && image.getHeight() >= minHeight) {
//                                            return url;
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println("Error processing image: " + url);
//                                       
//                                        if (isFileSizeGreaterThan(new URL(url), 70 * 1024)) {
//                                            return url;
//                                        }
//                                    }
//                                }
//                            }
//                        } else if (imageElement.isJsonPrimitive()) {
//                            String url = imageElement.getAsString();
//                            if (url.toLowerCase().contains(".jpg")) {
//                                try {
//                                    URL imgUrl = new URL(url);
//                                    BufferedImage image = ImageIO.read(imgUrl);
//                                    if (image != null && image.getWidth() >= minWidth && image.getHeight() >= minHeight) {
//                                        return url;
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("Error processing image: " + url);
//                                   
//                                    if (isFileSizeGreaterThan(new URL(url), 70 * 1024)) {
//                                        return url;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static boolean isFileSizeGreaterThan(URL url, int sizeInBytes) {
//        try {
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("HEAD");
//            int contentLength = connection.getContentLength();
//            return contentLength > sizeInBytes;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//   
}