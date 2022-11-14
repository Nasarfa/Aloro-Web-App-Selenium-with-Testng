package com.aloro.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aloro.actiondriver.Action;
import com.aloro.base.BaseClass;

import io.qameta.allure.Step;
import io.qameta.allure.Stories;

/**
 * @author Nasar
 *
 */
public class LoginPage extends BaseClass {
	Action action= new Action();
	
	@FindBy(id = "user_email")
	private WebElement userid;
	@FindBy(id = "user_password")
	private WebElement password;
	@FindBy(id = "login_button")
	private WebElement Loginbutton;
	@FindBy(xpath = "//*[@id=\"sidebar1\"]/div[2]/ul/li[1]/a")
	private WebElement verifylogin;
	
	public LoginPage(){
		PageFactory.initElements(getDriver(), this);
	}
	@Step("Enter the user name: {0} step...")
	public void enter_user_name(String Username) {
		action.type(userid, Username);
	}
	@Step("Enter the password: {1} step...")
	public void enter_password(String Password) {
		action.type(password, Password);
	}
	@Step("Click on Login Button step...")
	public HomePage click_login_button() {
		action.click(getDriver(), Loginbutton);
		return new HomePage();	
	}
	@Step("Verifying home page displayed or not step...")
	public Boolean verify_login() {
	    return action.isDisplayed(getDriver(),verifylogin);
	}
	@Step("Verifying Login button displayed or not step...")
	public boolean verify_web_launch() {
		return action.isDisplayed(getDriver(), Loginbutton);
	}
}
