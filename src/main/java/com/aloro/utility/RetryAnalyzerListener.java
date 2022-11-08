package com.aloro.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aloro.base.BaseClass;

public class RetryAnalyzerListener implements IRetryAnalyzer
{
	BaseClass bass=new BaseClass();
	public static Properties prop;

	int counter = 0;


	public boolean retry(ITestResult result) {
		try {
			prop=new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/Configuration/Config.properties");
			prop.load(ip); 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int retrylimit=Integer.parseInt(prop.getProperty("RetryCount"));
		if(counter < retrylimit)
		{
			System.out.println("Retry");
			counter++;
			return true;
		}
		return false;
	}
}

