package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}	
	
	
	private final By header = By.tagName("h1");
	private final By productImages = By.cssSelector("ul.thumbnails img");
	private final By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	
	public String getProductHeader() {
		String headerVal = eleUtil.waitForElementVisible(header, AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("Product header is: "+ headerVal);
		return headerVal;
	}
	
	public int getProductImages() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		System.out.println("Total number of product images: "+ imagesCount);
		return imagesCount;
		
	}
	
	public void addProductToCart(String quantity) {
		System.out.println("Quantity of products to add to cart: "+ quantity);
		WebElement quantityEle = eleUtil.waitForElementVisible(By.xpath("//input[@name='quantity']"), AppConstants.DEFAULT_SHORT_WAIT);
		quantityEle.clear();
		quantityEle.sendKeys(quantity);
		eleUtil.doClick(By.cssSelector("button#button-cart"));
		
	}
	
	public String CaptureSuccessMsg() {
		String successMsg = eleUtil.waitForElementVisible(By.cssSelector("div.alert"), AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("MSG: "+successMsg);
		return successMsg;
	}
	
	public void selectShoppingCartLink() {
		eleUtil.waitForElementVisible(By.xpath("//div/a[text()='shopping cart']"), AppConstants.DEFAULT_SHORT_WAIT).click();
	}
	
	
	public Map<String, String> getProductData() {
//		productMap = new HashMap<String,String>(); // random order
		
//		productMap = new LinkedHashMap<String,String>(); // Exact insertion order
		
		productMap = new TreeMap<String,String>(); // Sorted order with respect to the Keys
		
		productMap.put("productName", getProductHeader());
		productMap.put("productImages", String.valueOf(getProductImages()));
		
		getProductMetaData();
		getProductPriceData();
		
		System.out.println("============Complete Product Data============: \n" + productMap);
		return productMap;
	}
	
	
//	Brand: Apple
//	Product Code: Product 16
//	Reward Points: 600
//	Availability: In Stock
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForElementsVisible(productMetaData, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("Total meta data: "+ metaList.size());
		
			for(WebElement e: metaList) {
			String metaData = e.getText();
			String meta[] = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
		
	}
	
//	$602.00
//	Ex Tax: $500.00
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.waitForElementsVisible(productPriceData, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("Total price data: "+ priceList.size());
		
		String priceValue = priceList.get(0).getText();
		String exTaxValue = priceList.get(1).getText().split(":")[1].trim();
		
		productMap.put("productPrice", priceValue);
		productMap.put("exTaxPrice", exTaxValue);
		
		
	}
}
