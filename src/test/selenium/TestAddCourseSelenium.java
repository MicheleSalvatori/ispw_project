package test.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Luca Santopadre 0257118
 */

public class TestAddCourseSelenium {
	
	private WebDriver driver;
	WebElement submit;
	String expectedStatus = "http://localhost:8080/ispw_project/CoursePageServlet?course=_testSelenium";
	String status = "";

	@Before
	public void setupTest() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/ispw_project/LoginServlet");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("testStudent");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("testPassword");
		driver.findElement(By.xpath("//*[@id=\"login\"]")).click();	
	}
	
	@Test
	public void test() throws InterruptedException {	
		
		String profilePath = "html/body/div[1]/table/tbody/tr[4]/td/a";
		String addCourseButtontPath = "html/body/div[3]/div[2]/div[3]/button[1]";
		String selectOptionTestCoursePath = "html/body/div[4]/form/div/div[2]/select/option[2]";
		String buttonSubmitPath = "html/body/div[4]/form/div/div[3]/button";
		String courseStatusPath = "html/body/div[3]/div[2]/div[2]/div/table/tbody/tr[2]/td[1]/table/tbody/tr[1]/td/a";
		
		driver.findElement(By.xpath("/"+profilePath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("/"+addCourseButtontPath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("/"+selectOptionTestCoursePath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("/"+buttonSubmitPath)).click();
		
		Thread.sleep(2000);
		status = driver.findElement(By.xpath("/"+courseStatusPath)).getAttribute("href");
		System.out.println(status);
		assertEquals(expectedStatus, status);
	}
	
	@After
	public void closeTest() throws InterruptedException {
		if(expectedStatus.equals(status)) {
			String buttonDeleteRequestPath = "html/body/div[3]/div[2]/div[2]/div/table/tbody/tr[2]/td[2]/form/button";
			Thread.sleep(2000);
			driver.findElement(By.xpath("/"+buttonDeleteRequestPath)).click();
			Thread.sleep(1000);
			driver.switchTo().alert().accept();
		}
		String logoutButtonPath = "html/body/div[2]/table/tbody/tr/td[4]/a/button";
		Thread.sleep(2000);
		driver.findElement(By.xpath("/"+logoutButtonPath)).click();
		driver.close();
		driver.quit();

	}
}
