package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	// Every page will have it's locators also known as Page objects
	// private By locators : Page objects

	private final By header1 = By.xpath("//h2[contains(text(), 'New')]");
	private final By header2 = By.xpath("//h2[contains(text(), 'Returning')]");
	private final By searchBar = By.name("search");
	private final By searchBtn = By.xpath("//span/button[@type='button']");
	private final By emailID = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@type='submit']");
	private final By forgotPwdLink = By.xpath("(//a[text()='Forgotten Password'])[1]");
	private final By registerLink = By.linkText("Register");

	private final By loginErrorMesg = By.cssSelector("div.alert.alert-danger.alert-dismissible");

	// public constructor

	public LoginPage(WebDriver driver) {
		this.driver = driver;

		eleUtil = new ElementUtil(driver);
	}

	// public page methods/actions

	@Step("Getting login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
		// System.out.println("Login page title: " + title);
		log.info("Login page title: " + title);
		return title;
	}

	@Step("Getting login page URL")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
		// System.out.println("Login page URL: " + url);
		log.info("Login page URL: " + url);
		return url;
	}

	@Step("Forgot password link exist")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}

	@Step("Login page header exist")
	public boolean isHeaderExist() {
		return eleUtil.isElementDisplayed(header1);

	}

	@Step("Login with valid username: {0} and password: {1}")
	public AccountsPage doLogin(String appUserName, String appPwd) {
		// System.out.println("Application credentials: " + appUserName +":" + appPwd);
		log.info("Application credentials: " + appUserName + ":" + appPwd);
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(appUserName);
		eleUtil.doSendKeys(password, appPwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}

	@Step("Login with invalid username: {0} and password: {1}")
	public boolean doLoginWithInvalidCredentials(String InvalidUserName, String InvalidPassword) {
		log.info("Invalid application credentials: " + InvalidUserName + ":" + InvalidPassword);
		WebElement emailIDEle = eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT);
		emailIDEle.clear();
		emailIDEle.sendKeys(InvalidUserName);
		eleUtil.doSendKeys(password, InvalidPassword);
		eleUtil.doClick(loginBtn);
		String invalidLoginErrorMesg = eleUtil.doElementGetText(loginErrorMesg);
		log.info("Invalid credentials error message: " + invalidLoginErrorMesg);

		if (invalidLoginErrorMesg.contains(AppConstants.INVALID_BLANK_CRED_MESG)) {
			return true;
		} 
		else if (invalidLoginErrorMesg.contains(AppConstants.INVALID_LOGIN_CRED_MESG)) {
			return true;
		}
		return false;
	}

	@Step("Navigating to Registration page")
	public RegisterPage navigateToRegisterPage() {
		log.info("Trying to navigate to registration page");
		eleUtil.waitForElementVisible(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();
		;
		return new RegisterPage(driver);
	}

}
