package demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MetaTagContentGetter {
	//they consist of overriden functions
    public static String getMetaTagContent(String pageSource, String property) {
        Document doc = Jsoup.parse(pageSource);
        String content = doc.select("meta[property=" + property + "]").attr("content");
        if (content.isEmpty()) {
            content = doc.select("meta[name=" + property + "]").attr("content");
        }
        return content;
    }

    public static String getMetaTagContent(WebDriver driver, WebDriverWait wait, String property) {
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
   
    public static String getMetaTagContent(String pageSource, String attribute, String value) {
        Document doc = Jsoup.parse(pageSource);
        String content = doc.select("meta[" + attribute + "=" + value + "]").attr("content");
        return content;
    }
   
    public static String getMetaTagContent(WebDriver driver, WebDriverWait wait, String attribute, String value) {
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
}