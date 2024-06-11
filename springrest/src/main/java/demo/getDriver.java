package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class getDriver {
	public static WebDriver initializeWebDriver() {
       
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments(
        "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);
        return driver;
        
        
	    }
}