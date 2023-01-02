package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic: 100: Login Page for open cart application")
@Feature("Login page")
@Story("Login: 101: Design login page with various fetaures")
public class LoginPageTest extends BaseTest {

	@Description("Test Login page title")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "check the login page title", priority = 1)
	public void loginPageTitleTest() {

		String actTitle = loginPage.getLoginPageTitle();

		System.out.println("Login page title is : " + actTitle);

		Assert.assertEquals(actTitle, Constants.LOGIN_PAGE_TITLE);

	}

	@Description("Test Login Page URL")
	@Severity(SeverityLevel.MINOR)
	@Test(description = "check the login page url", priority = 2)
	public void loginPageURLTest() {

		String actUrl = loginPage.getLoginPageUrl();

		System.out.println("Login page url is : " + actUrl);

		Assert.assertTrue(actUrl.contains(Constants.LOGIN_PAGE_URL_FRACTION));

	}

	@Description("Test Forgot Password Link Exist on Login Page")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "check the forgot pwd link is displayed", priority = 3)
	public void forgotPwdLinkTest() {

		Assert.assertTrue(loginPage.isForgotPwdLinkExist());

	}

	@Description("Test Register Link Exist on Login Page")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "check the register link is displayed" , priority = 4)
	public void registerLinkTest() {

		Assert.assertTrue(loginPage.isRegisterLinkExist());

	}

	@Description("Test login functionality is working with given username and password")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "check the login with username and password" ,priority = 5)
	public void loginTest() {

		Assert.assertTrue(loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim())
			.isLogoutLinkExist());

	}
	

}
