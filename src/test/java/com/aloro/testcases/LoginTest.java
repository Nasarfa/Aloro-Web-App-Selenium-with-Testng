package com.aloro.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aloro.base.BaseClass;
import com.aloro.dataprovider.DataProviders;
import com.aloro.pageobject.LoginPage;
import com.aloro.utility.Log;
import com.aloro.utility.VideoRecorder;

public class LoginTest extends BaseClass{

	private LoginPage loginpage;

	@Parameters("browser")
	@BeforeMethod(groups = {"Smoke","Sanity","Regression"})
	public void setup(String browser) throws Exception {
		launchApp(browser); 
	}

	@AfterMethod(groups = {"Smoke","Sanity","Regression"})
	public void tearDown() throws Exception {
		getDriver().quit();
	}
	@Test(priority = 0,groups= {"Smoke","Sanity","Regression"})
	public void Verify_Webapp_launch_Tc01() {
		loginpage=new LoginPage();
		Log.startTestCase("Login Test Case Execution Start here");
		Log.info("Verification start here");
		boolean launch_verify=loginpage.verify_web_launch();
		Assert.assertTrue(launch_verify);
		Log.endTestCase("Test Case Execution End here");
	}
	
	@Test(priority = 1,groups= {"Smoke","Sanity","Regression"},dataProvider="credentials",dataProviderClass = DataProviders.class)	
	public void Verify_Login_Tc02(String Username,String Password) throws Throwable {
		loginpage=new LoginPage();
	    Log.startTestCase("Login Test Case Execution Start here");
		loginpage.enter_user_name(Username);
		loginpage.enter_password(Password);
		loginpage.click_login_button();
		Boolean login=loginpage.verify_login();
		Log.info("Verification of Login Start");
        Assert.assertTrue(login);
		Log.endTestCase("Login Test Case Execution End here");
	}
}
