package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

import io.qameta.allure.Description;

import com.aventstack.chaintest.plugins.ChainTestListener;

//@Listeners(ChainTestListener.class)
//@Listeners({ChainTestListener.class, TestAllureListener.class})

public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;	
	protected RegisterPage registerPage;
	protected CommonsPage commonsPage;
	
	@Description("Launch the browser: {0} and URL")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(@Optional("chrome") String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		commonsPage = new CommonsPage(driver);
	}
	
	@Description("Closing the browser")
	@AfterMethod // will be running after each @test method
	public void attachScreenshot(ITestResult result) {
		
		if (!result.isSuccess()) {// only for failure test cases -- true
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}

		//ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");

	}
	
	

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
