package demo;

import org.openqa.selenium.WebDriver;
import java.lang.*;

public class first {
	public static void main(String[] args)
	{
		String url = "https://www.ft.com/";
		
		WebDriver driver = getDriver.initializeWebDriver();
		
		twitterNew.preview(url, driver);
		System.out.println();
		facebookNew.preview(url, driver);
		System.out.println();
		LinkedInNew.preview(url, driver);
		System.out.println();
		
		driver.quit();
	}
}