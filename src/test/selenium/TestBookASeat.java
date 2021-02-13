package test.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBookASeat {

	private WebDriver driver;
	private String expectedStatus = "yourSeat";
	String seatStatus;
	WebElement seat;

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
	public void test() {
		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/div[1]/table/tbody/tr/td[5]/form/button")).click();
		seat = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/form/table/tbody/tr[1]/td[1]/button"));
		seat.click();
		driver.switchTo().alert().accept();
		
		seatStatus = seat.getAttribute("name");
		assertEquals(expectedStatus, seatStatus);
	}
	
	
	@After
	public void closeTest() {
		if (seatStatus.equals(expectedStatus)) {
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/form/table/tbody/tr[1]/td[1]/button")).click();
			driver.switchTo().alert().accept();
		}
		driver.close();
		driver.quit();
	}

}