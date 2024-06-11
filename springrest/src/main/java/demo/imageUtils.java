package demo;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class imageUtils {
	public static boolean isImageTooSmall(String imageUrl, int minW, int minH) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            return image.getWidth() < minW || image.getHeight() < minH;
        } catch (Exception e) {
            return true;
        }
    }

    public static String findFirstLargeImage(WebDriver driver, int minWidth, int minHeight) {
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
}