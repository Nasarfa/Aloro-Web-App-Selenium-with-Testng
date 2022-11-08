package com.aloro.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aloro.actiondriver.Action;
import com.aloro.base.BaseClass;

public class HomePage extends BaseClass {
	
	Action action =new Action();
	
	public HomePage(){
		PageFactory.initElements(getDriver(), this);
	}
	
}
