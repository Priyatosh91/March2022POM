package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private By productHeader = By.cssSelector("div#content h1");
	private By productDetails = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By addToCart = By.cssSelector("button#button-cart");
	private By successfullAddMessage = By.cssSelector("div.alert.alert-success.alert-dismissible");

	public ProductInfoPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public String getProductHeaderName() {

		return eleUtil.WaitForElementVisible(productHeader, Constants.DEFAULT_TIME_OUT).getText();
	}

	public Map<String, String> getProductDetails() {

		List<String> productDetailsLists = eleUtil.getElementsTextList(productDetails);

		Map<String, String> map = new HashMap<String, String>();

		for (String list : productDetailsLists) {

			String name[] = list.split(":");

			String key = name[0].trim();
			String value = name[1].trim();

			map.put(key, value);

		}

		return map;
	}

	public String getProductInfoPageText() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		String pagetext = js.executeScript("return document.documentElement.innerText").toString();

		System.out.println("------------------------\n" + pagetext + "-------------------------\n");

		return pagetext;
	}

	public void doClickAddToCart() {

		eleUtil.WaitForElementVisible(addToCart, Constants.DEFAULT_TIME_OUT).click();
	}

	public void getSuccuesfullMessage() {

	String text = 	eleUtil.WaitForElementVisible(successfullAddMessage, Constants.DEFAULT_TIME_OUT).getText();
	System.out.println(text);

		
	}
}
