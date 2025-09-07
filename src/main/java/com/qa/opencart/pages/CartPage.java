package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {
	
	String c1 = "Cart";
	By cart = By.name(c1);
	
	public void addToCart() {
		System.out.println("add products to cart");
	}

}
