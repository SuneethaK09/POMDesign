package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("EP_001: Design the Open Cart Application Login Page")
@Feature("F_001: Design Open Cart Login Feature")
@Story("US_001: Develop login core features: Title, URL, User is able to login")
public class LoginPageTest extends BaseTest{
	
	
	@Description("Login page title test")
	@Owner("Suneetha")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		ChainTestListener.log("Login page title: "+ actualTitle);
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
		
	}
	
	
	@Description("Login page url test")
	@Owner("Suneetha")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String actualURL = loginPage.getLoginPageURL();
		ChainTestListener.log("Login page url: "+ actualURL);
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Description("Forgot password link exist test")
	@Owner("Suneetha")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
		
	}
	
	@Description("Login page header test")
	@Owner("Suneetha")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void isLoginPageHeaderExist() {
		Assert.assertTrue(loginPage.isHeaderExist());
	}
	
	@Description("User is able to login to application with valid credentials")
	@Owner("Suneetha")
	@Severity(SeverityLevel.BLOCKER)
	@Test (priority = Integer.MAX_VALUE)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	

}
