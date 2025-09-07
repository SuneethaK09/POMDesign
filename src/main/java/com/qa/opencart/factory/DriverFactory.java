package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlightEle;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	public OptionsManager optionsManager;

	/**
	 * This method is used for initializing the driver on the basis of browser
	 * 
	 * @param browserName
	 * @returns the driver instance
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		// System.out.println("Browser name: "+ browserName);
		log.info("Browser name: " + browserName);

		highlightEle = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome": {
			// driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		}
		case "firefox": {
			// driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		}
		case "edge": {
			// driver = new EdgeDriver();
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		}
		case "safari": {
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		}
		default:
			// System.out.println(AppError.INVALID_BROWSER_MESG + ":" + browserName);
			log.error(AppError.INVALID_BROWSER_MESG + ":" + browserName);
			throw new FrameworkException("==========INVALID BROWSER==========");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	/**
	 * This method is used to get the local copy of driver at any time
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used for initializing the prop with Properties file
	 * 
	 * @returns the properties instance
	 */

	// mvn clean install -Denv
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		log.info("=======Environment name=======" + envName);

		try {
			if (envName == null) {
				log.warn("*****No environment is provided. Hence running test cases in QA environment by default*****");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
			} else {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.prod.properties");
					break;

				default:
					log.error(
							"*****Invalid Environment name is provided. Plese provide the right environment name*****");
					throw new FrameworkException("********Invalid environment name********");
				}

			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}

	/**
	 * This method is used to take screenshot
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}

}
