package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTest {
	
	String s = "Cart";
	int productCount = 5;
	
	@Test
	public void addToCartTest() {
		System.out.println("Cart page testing");
		Assert.assertTrue(true);
	}

}
