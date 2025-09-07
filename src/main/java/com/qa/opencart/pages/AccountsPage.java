package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private final By headers = By.xpath("//div[@id='content']/h2");
	private final By logoutLink = By.linkText("Logout");
	private final By searchBar = By.name("search");
	private final By searchIcon = By.cssSelector("span button");
	
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headersList = eleUtil.waitForElementsPresence(headers, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("Total number of headers: " + headersList.size());
		
		List<String> headerTextList = new ArrayList<String>();
		for (WebElement e: headersList) {
			String headerText = e.getText();
			headerTextList.add(headerText);			
		}
		return headerTextList;
	}
	
	public boolean isLogoutLinkExist() {
		WebElement logoutEle = eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_WAIT);
		return eleUtil.isElementDisplayed(logoutEle);
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("Searching for products with: "+ searchKey);
		WebElement searchEle = eleUtil.waitForElementVisible(searchBar, AppConstants.DEFAULT_MEDIUM_WAIT);
		searchEle.clear();
		searchEle.sendKeys(searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
		
		
	}
	
	
	
}
