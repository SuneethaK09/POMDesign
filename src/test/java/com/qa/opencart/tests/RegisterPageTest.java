package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CsvUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

public class RegisterPageTest extends BaseTest {

	// BT(Chrome+loginURL) --> BC(move to Register page) --> @Test

	@BeforeClass
	public void goToRegisterPage() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	@DataProvider
	public Object[][] getRegisterData() {
		return new Object[][] {
			{ "Ritu", "Sheru", "8790221043", "Ritu@123", "Yes" },
			{ "Neha", "Ghosh", "9980485900", "Neha@123", "No" },
			{ "Rony", "Roy", "6301232467", "Rony@123", "No" }
		};
	}

	@DataProvider
	public Object[][] getRegSheeetData() {
		return ExcelUtil.getTestData("RegisterData");
	}
	
	@DataProvider
	public Object[][] getRegCSVData() {
		return CsvUtil.csvData("RegisterData");
	}
	
	@Test(dataProvider = "getRegCSVData")
	public void registerTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage.userRegister(firstName, lastName, StringUtil.getRandomEmail(), telephone, password, subscribe));

	}

}
