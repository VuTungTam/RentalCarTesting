package test_login;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LoginTest {
	private WebDriver driver;

	@BeforeSuite
	void setUp() {
		System.setProperty("webdriver.chrome.driver", "D:\\KT\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@BeforeMethod
	void beginTestCase() {
		driver.get("http://localhost:3000/");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in")));
		loginLink.click();
	}

	@Test(description = "TC_001: Verify empty email input")
	void testEmptyEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Email is required"));

		Assert.assertTrue(foundError,
				"Error message 'Email is required' should not appear when a valid full name is provided.");
	}
	
	@Test(description = "TC_002: Verify not empty email input")
	void testNotEmptyEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("a");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Email is required"));

		Assert.assertFalse(foundError,
				"Error message 'Email is required' should not appear when a valid full name is provided.");
	}
	
	@Test(description = "TC_003: Verify valid email input")
	void testValidEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test@gmail.com");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		Boolean isValid = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checkValidity();",
				emailInput);

		Assert.assertTrue(isValid, "Field should be valid");
	}
	
	@Test(description = "TC_004: Verify invalid email input")
	void testInvalidEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("testgmail.com"); 

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		Boolean isValid = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checkValidity();",
				emailInput);

		Assert.assertFalse(isValid, "Field should be invalid");
	}

	@Test(description = "TC_005: Enter unregistered email")
	void testUnregisteredEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("adsffsdfv@gmail.com");
		
		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("WrongPass1");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(text(), 'Either email address or password is incorrect')]")));
		
		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
	}
	
	@Test(description = "TC_006: Enter registered email")
	void testRegisteredEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("adsffsdfv@gmail.com");
		
		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("WrongPass1");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(text(), 'Either email address or password is incorrect')]")));
		
		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
	}
	
	@Test(description = "TC_007: Verify empty password input")
	void testEmptyPassword() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Password is required"));

		Assert.assertTrue(foundError, "Error message 'Password is required' should appear when password is empty.");
	}
	
	@Test(description = "TC_008: Verify valid password input")
	void testValidPassword() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("StrongPassword123");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Password is required"));

		Assert.assertFalse(foundError,
				"Error message 'Password is required' should not appear when a valid password is provided.");
	}

	@Test(description = "TC_009: Verify wrong password input")
	void testWrongInfor() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test@gmail.com");
		
		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("WrongPass1");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(text(), 'Either email address or password is incorrect')]")));
		
		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
	}
	
	@Test(description = "TC_010:Test login success")
	void testLoginSuccess() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test@gmail.com");
		
		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("a");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();
		
		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(text(), 'Welcome, Tester')]")));
		
		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
		
		WebElement profile = wait
				.until(ExpectedConditions.elementToBeClickable(By.className("img-profile")));
		profile.click();
		
		WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Log out']")));
		
		
		logoutButton.click();
		WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));

	    // Tìm nút "OK" trong modal và click
	    WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Ok')]")));
	    okButton.click();
	}
	
	@AfterSuite
	void tearDown() {
		driver.quit();
	}
}
