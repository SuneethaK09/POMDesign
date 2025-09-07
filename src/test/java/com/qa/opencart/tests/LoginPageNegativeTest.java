package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest {
	
	
	@DataProvider
	public Object[][] getNagativeLoginData(){
		return new Object[][] {
			{"testng123@gmail.com", "testttt@123"},
			{"march2024@open.com", "testinggg@123"},
			{"sel2025@open.com", "Seleniummm@123"},
			{"", "test@123"},
			{"", ""}
		};
	}
	
	
	@Test(dataProvider = "getNagativeLoginData")
	public void negativeLoginTest(String invalidUsername, String invalidPassword) {
		Assert.assertTrue(loginPage.doLoginWithInvalidCredentials(invalidUsername, invalidPassword));
		
	}
	
	
	

}
