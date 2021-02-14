package test.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * Gabriele Quatrana 0253513
 * Test login with blank fields
 */

public class TestLoginSelenium {
	
	private WebDriver driver;

	@Before
	public void setupTest() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test
	public void testLogin() {
		driver.get("http://localhost:8080/ispw_project/LoginServlet");
		
		//Login with blank fields
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("");
		driver.findElement(By.xpath("//*[@id=\"login\"]")).click();
		
		String excpected = "One or more fields are empty.";
		String result = driver.switchTo().alert().getText();
		
		//Check alert value
		assertEquals(excpected, result);
	}
	
	@After
	public void closeTest() {
		driver.switchTo().alert().accept();
		driver.close();
		driver.quit();
	}
}
