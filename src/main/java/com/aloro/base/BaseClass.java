package com.aloro.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.ietf.jgss.Oid;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.aloro.utility.ExtentManager;
import com.aloro.utility.VideoRecorder;
import com.aloro.utility.ZipCompress;
import com.beust.jcommander.Parameter;



import io.github.bonigarcia.wdm.WebDriverManager;
/**
 * @author Nasar: BaseClass is used to load the config file and Initialize 
 * WebDriver
 *  
 */
public class BaseClass {
	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	public static Properties prop;
	//loadConfig method is to load the configuration
	@BeforeSuite(groups = { "Smoke", "Sanity", "Regression" })
	public void loadConfig() throws Exception {
		ExtentManager.setExtent();
		DOMConfigurator.configure("Logs/log4j.xml");

		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/Configuration/Config.properties");
			prop.load(ip); 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Video Record Start
		String record=prop.getProperty("VideoRecording");
		if(record.equalsIgnoreCase("True")) {
			VideoRecorder.startRecord("Aloro Test Case Execution Video");
		}
		//Deleting screenshot from the directory
		String Screen_location=prop.getProperty("screenshotlocation");
		File filelocation=new File(Screen_location);
		File[] listfiles=filelocation.listFiles();
		for (File file : listfiles) {
			file.delete();
		}	
	}
	
	public static WebDriver getDriver() {
		// Get Driver from threadLocalmap
		return driver.get();
	}

	public void launchApp(String browserName) {
	 
 
		// String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("Chrome")) {
	
			WebDriverManager.chromedriver().setup();
			ChromeOptions options=new ChromeOptions();
			Boolean headless=Boolean.valueOf(prop.getProperty("HeadlessMode"));
			options.setHeadless(headless);
			//Set Browser to ThreadLocalMap
			driver.set(new ChromeDriver(options));
		} else if (browserName.equalsIgnoreCase("FireFox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			Boolean headless=Boolean.valueOf(prop.getProperty("HeadlessMode"));
			options.setHeadless(headless);
			driver.set(new FirefoxDriver(options));
		} else if (browserName.equalsIgnoreCase("IE")) {
			WebDriverManager.iedriver().setup();
			driver.set(new InternetExplorerDriver());
		}
		//Maximize the screen
		getDriver().manage().window().maximize();
		//Delete all the cookies
		getDriver().manage().deleteAllCookies();
		//Implicit TimeOuts
		getDriver().manage().timeouts().implicitlyWait
		(Integer.parseInt(prop.getProperty("implicitWait")),TimeUnit.SECONDS);
		//PageLoad TimeOuts
		getDriver().manage().timeouts().pageLoadTimeout
		(Integer.parseInt(prop.getProperty("pageLoadTimeOut")),TimeUnit.SECONDS);
		//Launching the URL
		getDriver().get(prop.getProperty("url"));
	}

	@AfterSuite(groups = { "Smoke", "Regression","Sanity" })
	public void afterSuite() throws Exception {
		ExtentManager.endReport();
		String record=prop.getProperty("VideoRecording");
		String zipcompress=prop.getProperty("ExtentReportlocation");
		if(record.equalsIgnoreCase("True")) {
		VideoRecorder.stopRecord();
		ZipCompress.compress(zipcompress);
	}
	}
}


