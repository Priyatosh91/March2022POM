package com.qa.opencart.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {

		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());

	}

	@Test(priority = 1)
	public void accPageTitleTest() {

		String accTitle = accPage.getAccountpageTitle();

		System.out.println("Account page title is : " + accTitle);

		Assert.assertEquals(accTitle, Constants.ACCOUNTS_PAGE_TITLE);

	}

	@Test(priority = 2)
	public void accPageUrlTest() {

		String accUrl = accPage.getAccountpageUrl();

		System.out.println("Account page url is : " + accUrl);

		Assert.assertTrue(accUrl.contains(Constants.ACCOUNTS_PAGE_URL_FRACTION));

	}

	@Test(priority = 3)
	public void accPageHeaderSectionTest() {

		List<String> accPageSectionList = accPage.getAccountPageSectionsList();
		System.out.println("Account sections list are : " + accPageSectionList);

		Assert.assertEquals(accPageSectionList, Constants.ACCOUNTS_PAGE_SECTION_LIST);

	}

	@Test(priority = 4)
	public void isAccountHeaderTextExistTest() {

		String accountHeader = accPage.getAccountHeaderText();

		System.out.println("Account Page header .." + accountHeader);

		Assert.assertEquals(accountHeader, Constants.ACCOUNTS_PAGE_HEADER);

	}

	@Test(priority = 5)
	public void isSearchFieldExist() {

		Assert.assertTrue(accPage.isSearchFieldExist());

	}

	@Test(priority = 6)
	public void doLogoutTest() {

		Assert.assertTrue(accPage.clickOnLogOut().isLogoutHeaderExist());

	}

	@DataProvider
	public Object[][] getSearckKey() {

		return new Object[][] {

				{ "Macbook" }, { "iMac" }, { "Samsung" }, { "iphone" }

		};
	}

	@Test(dataProvider = "getSearckKey" , priority = 7)
	public void doSearchTest(String searchKey) {

		Assert.assertTrue(accPage.doSearch(searchKey).getSearchResultsCount() > 0);
	}

	@DataProvider
	public Object[][] getProductName() {

		return new Object[][] {

				{ "Macbook", "MacBook" }, { "Macbook", "MacBook Air" }, { "Macbook", "MacBook Pro" },
				{ "iMac", "iMac" }, { "Samsung", "Samsung Galaxy Tab 10.1" }, { "iphone", "iPhone" }

		};
	}

	@Test(dataProvider = "getProductName" , priority = 8)
	public void selectProductTest(String searchKey, String productName) {

		String productHeader = accPage.doSearch(searchKey).selectProduct(productName).getProductHeaderName();

		Assert.assertEquals(productHeader, productName);

	}

	@DataProvider
	public Object[][] getProductDetail() {

		return new Object[][] {

				{ "Macbook", "MacBook" }, { "Macbook", "MacBook Air" }, { "Macbook", "MacBook Pro" },
				{ "iMac", "iMac" }, { "Samsung", "Samsung Galaxy Tab 10.1" }, { "iphone", "iPhone" } };
	}

	@Test(dataProvider = "getProductDetail" , priority = 9)
	public void getProductDetailsTest(String searchKey, String productName) {

		Map<String, String> productDetail = accPage.doSearch(searchKey).selectProduct(productName).getProductDetails();

		productDetail.forEach((k, v) -> System.out.println(k + ":" + v));

	}

	@DataProvider
	public Object[][] getProductDescriptions() {

		return ExcelUtil.getTestData(prop.getProperty("file_path"), Constants.PRODUCT_SHEET_NAME);

		/*
		 * { "Macbook", "MacBook" }, { "Macbook", "MacBook Air" }, { "Macbook",
		 * "MacBook Pro" }, { "iMac", "iMac" }, { "Samsung", "Samsung Galaxy Tab 10.1"
		 * }, { "iphone", "iPhone" } };
		 */

	}

	@Test(dataProvider = "getProductDescriptions" , priority = 10)
	public void getProductDescriptionTest(String searchKey, String productName) {

		String text = accPage.doSearch(searchKey).selectProduct(productName).getProductInfoPageText();

		if (text.contains("MacBook")) {
			softAssert.assertTrue(text.contains("Intel Core 2 Duo processor"));
			// softAssert.assertEquals(text, "6GB memo");
			softAssert.assertTrue(text.contains("Sleek, 1.08-inch-thin design"));
			softAssert.assertTrue(text.contains("Built-in iSight camera"));

			softAssert.assertAll();
		}

		else if (text.contains("MacBook Air")) {

			softAssert.assertTrue(text.contains("MacBook Air is ultrathin, ultraportable"));
		}

		else if (text.contains("MacBook Pro\"")) {

			softAssert.assertTrue(text.contains("Latest Intel mobile architecture"));
		} else if (text.contains("iMac")) {

			softAssert.assertTrue(text.contains("Just when you thought iMac had everything"));
			softAssert.assertAll();
		}

	}

	@Test(priority = 11)
	public void addToCartTest() {

		productInfoPage = accPage.doSearch("Macbook").selectProduct("MacBook Air");
		productInfoPage.doClickAddToCart();
		productInfoPage.getSuccuesfullMessage();

	}

}
