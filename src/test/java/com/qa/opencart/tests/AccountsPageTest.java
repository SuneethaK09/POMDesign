package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	//BT(launch browser and URL) --> BC(login) --> @Test(execute tests)
	
	@BeforeClass
	public void accPageSetUp() {
	
	accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		accPage.isLogoutLinkExist();
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeaderstest() {
		List<String> actaulHeaderList = accPage.getAccPageHeaders();
		Assert.assertEquals(actaulHeaderList.size(), AppConstants.ACC_PAGE_HEADERS_COUNT );
		Assert.assertEquals(actaulHeaderList, AppConstants.expectedAccPageHeaderList);
	}
	


}
