package test_password;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class ChangePasswordTest {

	public WebDriver driver;

	@BeforeSuite
	void setUp() {
		try {
			System.setProperty("webdriver.chrome.driver", "E:\\chromedriver-win64\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			driver.get("http://localhost:3000");
			WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in")));
			loginLink.click();
			WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
			emailInput.sendKeys("abcd@gmail.com");

			WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
			passwordInput.sendKeys("abcd123"); 

			WebElement signUpButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
			signUpButton.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize WebDriver. Check ChromeDriver setup.");
		}
	}

	@BeforeMethod
	void beginTestCase() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.className("img-profile")));
		profile.click();

		WebElement profileButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='My profile']")));

		profileButton.click();
		WebElement securityButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.id("profile-tabs-tab-security")));
		securityButton.click();
	}

	@Test(description = "TC_001: Verify empty oldpassword field")
	void testEmptyOldPasswordFields() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		currentPasswordInput.sendKeys("");
		newPasswordInput.sendKeys("abcde123");
		confirmPasswordInput.sendKeys("abcde123");
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		saveButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("This field is required"));

		Assert.assertFalse(foundError, "Error message 'This field is required' should appear when Owner is not selected.");
	}
	
	@Test(description = "TC_002: Verify not empty oldpassword field")
	void testNotEmptyOldPasswordFields() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		currentPasswordInput.clear();
		newPasswordInput.clear();
		confirmPasswordInput.clear();
		currentPasswordInput.sendKeys("a");
		newPasswordInput.sendKeys("abcde123");
		confirmPasswordInput.sendKeys("abcde123");
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		saveButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("This field is required"));

		Assert.assertFalse(foundError,
				"Error message 'This field is required' should appear when Owner is not selected.");
	}
	@Test(description = "TC_003: Verify empty newpassword field")
	void testEmptyNewPasswordFields() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		currentPasswordInput.clear();
		newPasswordInput.clear();
		confirmPasswordInput.clear();
		currentPasswordInput.sendKeys("abcd123");
		newPasswordInput.sendKeys("");
		confirmPasswordInput.sendKeys("abcde123");
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		saveButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("This field is required"));

		Assert.assertTrue(foundError,
				"Error message 'This field is required' should appear when Owner is not selected.");
	}
	@Test(description = "TC_004: Verify new password not valid field")
	void testValidNewPasswordFields() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		currentPasswordInput.clear();
		newPasswordInput.clear();
		confirmPasswordInput.clear();
		currentPasswordInput.sendKeys("abcd123");
		newPasswordInput.sendKeys("abc");
		confirmPasswordInput.sendKeys("abc");
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		saveButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.xpath("//div[contains(text(), 'Password must contain at least one number, one numeral, and seven characters.')]")));
				Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
	}
	
	@Test(description = "TC_005: Verify empty confirm password field")
	void testEmptyConfirmPasswordFields() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		currentPasswordInput.clear();
		newPasswordInput.clear();
		confirmPasswordInput.clear();
		currentPasswordInput.sendKeys("abcd123");
		newPasswordInput.sendKeys("abcde123");
		confirmPasswordInput.sendKeys("");
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		saveButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Passwords do not match"));

		Assert.assertTrue(foundError, "Error message 'Passwords do not match' should appear when Owner is not selected.");
	}
	

	@Test(description = "TC_006: Verify new password and confirm password do not match")
	void testPasswordMismatch() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		currentPasswordInput.clear();
		newPasswordInput.clear();
		confirmPasswordInput.clear();
		currentPasswordInput.sendKeys("abcd123");
		newPasswordInput.sendKeys("abcde123");
		confirmPasswordInput.sendKeys("abcde");
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		saveButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Passwords do not match"));

		Assert.assertTrue(foundError, "Error message 'Passwords do not match' should appear when Owner is not selected.");
	}
	
	@Test(description = "TC_007: Verify valid password change")
	void testValidPasswordChange() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		
		currentPasswordInput.clear();
		currentPasswordInput.sendKeys("abcd123");
		newPasswordInput.clear();
		newPasswordInput.sendKeys("abcde123");
		confirmPasswordInput.clear();
		confirmPasswordInput.sendKeys("abcde123");
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		saveButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[contains(text(), 'Change password successful')]")));
		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
	}


	@AfterMethod
	void endTestCase() {
		// Không đóng trình duyệt tại đây để tránh mất session.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement currentPasswordInput = driver.findElement(By.name("oldPassword"));
		WebElement newPasswordInput = driver.findElement(By.name("newPassword"));
		WebElement confirmPasswordInput = driver.findElement(By.name("confirmNewPassword"));
		currentPasswordInput.sendKeys("");
		newPasswordInput.sendKeys("");
		confirmPasswordInput.sendKeys("");
	}

	@AfterSuite
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
