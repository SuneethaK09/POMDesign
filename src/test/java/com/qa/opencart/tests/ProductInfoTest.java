package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoTest extends BaseTest{
	
	
	//BT(launch browser and URL) --> BC(login) --> @Test(execute tests)
	
	@BeforeClass
	public void productInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProducts() {
		return new Object[][] {
		{"macbook air", "MacBook Air"},
		{"samsung", "Samsung Galaxy Tab 10.1"},
		{"imac", "iMac"},
		{"canon", "Canon EOS 5D"}
			};
		}

	@DataProvider
	public Object[][] getProductSheetData() {
		return ExcelUtil.getTestData("ProductData");
	}
	
	@DataProvider
	public Object[][] getProductCSVData() {
		return ExcelUtil.getTestData("ProductData");
	}
	
		
	@Test (dataProvider = "getProductCSVData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		String actualHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actualHeader, productName);
		}
		
	
	@DataProvider
	public Object[][] getProductImages() {
		return new Object[][] {
		{"macbook air", "MacBook Air", 4},
		{"samsung", "Samsung Galaxy Tab 10.1", 7},
		{"imac", "iMac", 3},
		{"canon", "Canon EOS 5D", 3}
			};
		}
	
	
	@Test (dataProvider = "getProductImages")
	public void productImagesCountTest(String searchKey, String productName, int imageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actualImagesCount = productInfoPage.getProductImages();
		Assert.assertEquals(actualImagesCount, imageCount);
		
	}
	
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook");
		Map <String, String> productDataMap = productInfoPage.getProductData();
		
		
		SoftAssert softAssert = new SoftAssert();
	
		softAssert.assertEquals(productDataMap.get("productName"),"MacBook");
		
		softAssert.assertEquals(productDataMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDataMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productDataMap.get("Product Code"), "Product 16");
		
		softAssert.assertEquals(productDataMap.get("productPrice"),"$602.00");
		softAssert.assertEquals(productDataMap.get("exTaxPrice"),"$500.00");
		
		softAssert.assertAll();

	}
	
	@Test
	public void productToCartTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook");
		productInfoPage.addProductToCart("3");
				
	}
	
	@Test
	public void successMsgCaptureTest() {
		String actualSuccessMsg = productInfoPage.CaptureSuccessMsg();
		ChainTestListener.log("Success message: "+ actualSuccessMsg );
		Assert.assertTrue(actualSuccessMsg.contains("Success: You have added MacBook to your shopping cart!"));

	}
	
	@Test
	public void switchtoShoppingCartLinkTest() {
		productInfoPage.selectShoppingCartLink();
			
	}

	
}
