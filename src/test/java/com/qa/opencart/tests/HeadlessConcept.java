package com.qa.opencart.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HeadlessConcept {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		
		WebDriverManager.edgedriver().setup();
		
		ChromeOptions co = new ChromeOptions();
		
	//	co.addArguments("--headless");
		co.addArguments("--incognito");
		co.setHeadless(false);
		
		WebDriver driver = new ChromeDriver(co);
		
		
		
		driver.get("https://www.google.com/");
		
		System.out.println("get title :" + driver.getTitle());
		
		driver.quit();
		

		

	}

}
