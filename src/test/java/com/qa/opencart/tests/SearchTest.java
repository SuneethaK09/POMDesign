package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest {

	// BT(launch browser and URL) --> BC(login) --> @Test(execute tests)

	@BeforeClass
	public void searchSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] searchProduct() {
		return new Object[][] {
			{"samsung", "Samsung Galaxy Tab 10.1"},
			{"canon", "Canon EOS 5D"}
				};
			}
		
	@Test (dataProvider = "searchProduct")
	public void searchTest(String searchKey, String ProductName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(ProductName);
		String actualHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actualHeader, ProductName);

	}

}
