package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.FrameworkExceptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();

	/**
	 * this method is used to initialize the webdriver on the basis of given browser
	 * name
	 * 
	 * @param
	 * @return it returns driver
	 */

	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();

		optionsManager = new OptionsManager(prop);

		System.out.println("browser name is : " + browserName);
		
		// mvn clean install -Dbrowser ="chrome"
	//	String browser = System.getProperty("browser");
		
	//	System.out.println("Browser passed from command line is : "+ browser);

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();

			// driver = new ChromeDriver();
			// driver = new ChromeDriver(optionsManager.getChromeOptions());

			threadLocal.set(new ChromeDriver(optionsManager.getChromeOptions()));

		}

		else if (browserName.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().browserVersion("108.0.1").setup();

			// driver = new FirefoxDriver();

			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());

			threadLocal.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

		}

		else if (browserName.equalsIgnoreCase("safari")) {

			// driver = new SafariDriver();

			threadLocal.set(new SafariDriver());

		} else if (browserName.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());

			threadLocal.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		}

		else {

			System.out.println("Please pass the right browser: " + browserName);
			throw new FrameworkExceptions("BrowserNotFoundException....");
		}

		/*
		 * driver.manage().deleteAllCookies(); driver.manage().window().maximize();
		 * driver.get(prop.getProperty("url").trim());
		 */

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());

		return getDriver();

	}

	/**
	 * get the thread local copy of driver
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {

		return threadLocal.get();
	}

	// This method is used to intiliaze the property
	/**
	 * this method is used to initialize the properties
	 */
	public Properties init_prop() {

		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv ="qa"
		String envName = System.getProperty("env");

		if (envName == null) {

			System.out.println("No env is given ..Hence running it on QA environment");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		}

		else {

			try {

				switch (envName.toLowerCase()) {

				case "qa":
					System.out.println("Running it on QA env");
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;

				case "dev":
					System.out.println("Running it on dev env");
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					System.out.println("Running it on stage env");
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "uat":
					System.out.println("Running it on uat env");
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					System.out.println("Running it on prod env");
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please pass the right env : " + envName);

					throw new FrameworkExceptions("EnvironmentNotFoundException...");
				// break;
				}
			}

			catch (Exception e) {

			}
		}
		try {
			prop.load(ip);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return prop;

	}

	public String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = "./" + "screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
