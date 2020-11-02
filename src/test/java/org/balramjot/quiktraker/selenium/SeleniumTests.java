package org.balramjot.quiktraker.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeleniumTests {
	
	@Autowired
	private WebDriver driver;
	
	@Test
	void testHomePage() {
		// Opens the login page for this web application
		driver.get("http://localhost:8080/");
		assertEquals("QuikTraker", driver.getTitle());
	}
	
	
	@Test
	void testSignIn() {
		driver.get("http://localhost:8080/signIn");
		// Locate username field by CSS selector and input "Joan"
		WebElement usernameField = driver.findElement(By.cssSelector("#login-form > div:nth-child(1) > input:nth-child(2)"));
		usernameField.sendKeys("john@doe.com");
		// Locate password field by CSS selector and input "joan1234"
		WebElement passwordField = driver.findElement(By.cssSelector("#login-form > div:nth-child(2) > input:nth-child(2)"));
		passwordField.sendKeys("Test@12");
		// Click the submit button
		driver.findElement(By.name("signInButton")).click();
		
		// Welcome page should be displayed - test welcome message
		WebElement welcomeMessage = driver.findElement(By.cssSelector(".user-name > strong:nth-child(1) > span:nth-child(1)"));
		assertEquals(welcomeMessage.getText(), "John");
		
	}
	
	
	
	@Test
	void testContactUs() {
		driver.get("http://localhost:8080/signIn");
		WebElement usernameField = driver.findElement(By.cssSelector("#login-form > div:nth-child(1) > input:nth-child(2)"));
		usernameField.sendKeys("john@doe.com");
		WebElement passwordField = driver.findElement(By.cssSelector("#login-form > div:nth-child(2) > input:nth-child(2)"));
		passwordField.sendKeys("Test@12");
		driver.findElement(By.name("signInButton")).click();
		
		driver.get("http://localhost:8080/contact");
		
		
		WebElement contactField = driver.findElement(By.cssSelector("#subject"));
		contactField.sendKeys("Selenium Test Subject");
		WebElement messageField = driver.findElement(By.cssSelector("#message"));
		messageField.sendKeys("Selenium Test Message");
		driver.findElement(By.cssSelector("button.btn")).click();
		
		WebElement successMessage = driver.findElement(By.cssSelector(".alert > strong:nth-child(1)"));
		assertEquals(successMessage.getText(), "Your message has been sent successfully");
		
	}
	
}
