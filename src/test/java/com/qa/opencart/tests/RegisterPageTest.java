package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {

		regPage = loginPage.goToRegisterPage();

	}
	
	@DataProvider
	public Object[][] getRegisterTestData() {
		
		return ExcelUtil.getTestData(prop.getProperty("file_path"),Constants.REGISTER_SHEET_NAME);
	}
	
	public String getRandomEmail() {
		
		Random random = new Random();
		String email = "august2022"+random.nextInt(1000)+"@gmail.com";
		
		return email;
		
	}

	@Test(dataProvider = "getRegisterTestData")
	public void doRegisterTest(String firstName , String lastName, String phoneNo, String password, String confirmPassword) {

		Assert.assertTrue(regPage.doAddPersonalDetails(firstName, lastName, getRandomEmail(), phoneNo,
				password, confirmPassword));

	}

}
