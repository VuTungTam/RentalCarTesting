package test_register;

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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class RegisterTest {

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
		WebElement signUpLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign up")));
		signUpLink.click();
	}

	@Test(description = "TC_001: Verify valid full name input")
	void testValidFullName() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("Name test");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Name is required"));

		Assert.assertFalse(foundError,
				"Error message 'Name is required' should not appear when a valid full name is provided.");
	}

	@Test(description = "TC_002: Verify empty full name input")
	void testEmptyFullName() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Name is required"));

		Assert.assertTrue(foundError, "Error message 'Name is required' should appear when full name is empty.");
	}

	@Test(description = "TC_003: Verify valid email input")
	void testValidEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test@gmail.com"); 

		emailInput.sendKeys(Keys.TAB);

		Boolean isValid = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checkValidity();",
				emailInput);

		Assert.assertTrue(isValid, "Field should be valid");
	}

	@Test(description = "TC_004: Verify invalid email input")
	void testInvalidEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Tìm phần tử input email
		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("testgmail.com"); 

		emailInput.sendKeys(Keys.TAB); 

		Boolean isValid = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checkValidity();",
				emailInput);

		Assert.assertFalse(isValid, "Field should be invalid");
	}

	@Test(description = "TC_005: Email already existed")
	void testEmailExisted() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("Name test");

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test1000@example.com");

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456789");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("StrongPassword123");

		WebElement confirmPasswordField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("confirmPassword")));
		confirmPasswordField.sendKeys("StrongPassword123");

		WebElement renterRadioButton = driver.findElement(By.id("renter"));
		renterRadioButton.click();

		wait.until(ExpectedConditions.elementToBeSelected(renterRadioButton));

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Email already existed')]")));

		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");

	}

	@Test(description = "TC_006: Verify empty email input")
	void testEmptyEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Email is required"));

		Assert.assertTrue(foundError, "Error message 'Email is required' should appear when email is empty.");
	}

	@Test(description = "TC_007: Verify valid phone input")
	void testValidPhone() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456789");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Phone number is required"));

		Assert.assertFalse(foundError,
				"Error message 'Phone number is required' should not appear when a valid phone number is provided.");
	}

	@Test(description = "TC_008: Verify invalid phone input")
	void testInvalidPhone() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Phone number is not valid"));

		Assert.assertFalse(foundError,
				"Error message 'Phone number is not valid' should not appear when a valid phone number is provided.");
	}

	@Test(description = "TC_009: Verify empty phone input")
	void testEmptyPhone() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		usernameInput.sendKeys("");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Phone number is required"));

		Assert.assertTrue(foundError,
				"Error message 'Phone number is required' should appear when phone number is empty.");
	}

	@Test(description = "TC_010: Verify valid password input")
	void testValidPassword() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("Name test");

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test1000@example.com");

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456789");

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

	@Test(description = "TC_011: Verify invalid password input")
	void testInvalidPassword() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("Name test");

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test1000@example.com");

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456789");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("abc");

		WebElement confirmPasswordField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("confirmPassword")));
		confirmPasswordField.sendKeys("abc");

		WebElement renterRadioButton = driver.findElement(By.id("renter"));
		renterRadioButton.click();

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(text(), 'Password must contain at least one number, one numeral, and seven characters.')]")));

		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
	}

	@Test(description = "TC_012: Verify empty password input")
	void testEmptyPassword() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("Name test");

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test1000@example.com");

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456789");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Password is required"));

		Assert.assertTrue(foundError, "Error message 'Password is required' should appear when password is empty.");
	}

	@Test(description = "TC_013: Validate that password and confirm password match")
	void testPasswordMatchCorrect() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("StrongPassword123");

		WebElement confirmPasswordField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("confirmPassword")));
		confirmPasswordField.sendKeys("StrongPassword123");

		WebElement renterRadioButton = driver.findElement(By.id("renter"));
		renterRadioButton.click();

		wait.until(ExpectedConditions.elementToBeSelected(renterRadioButton));

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		boolean isErrorMessageVisible = driver
				.findElements(By.xpath("//div[contains(text(), 'Password and confirm password must match')]"))
				.size() > 0;

		Assert.assertFalse(isErrorMessageVisible, "Toast should not appear because passwords match.");
	}

	@Test(description = "TC_014: Validate that password and confirm password not match")
	void testPasswordNotMatch() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("Name test");

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test1000@example.com");

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456789");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("WrongPassword123");

		WebElement confirmPasswordField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("confirmPassword")));
		confirmPasswordField.sendKeys("StrongPassword123");

		WebElement renterRadioButton = driver.findElement(By.id("renter"));
		renterRadioButton.click();

		wait.until(ExpectedConditions.elementToBeSelected(renterRadioButton));

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(text(), 'Password and confirm password must match')]")));

		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");
	}

	@Test(description = "TC_015: Validate that no role is chosen")
	void testNoRoleChosen() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Role is required"));

		Assert.assertTrue(foundError, "Error message 'Role is required' should appear when no role is chosen.");
	}

	@Test(description = "TC_016: Validate that Owner is chosen")
	void testOwnerChosen() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement ownerRadioButton = driver.findElement(By.id("owner"));
		ownerRadioButton.click();

		wait.until(ExpectedConditions.elementToBeSelected(ownerRadioButton));
		Assert.assertTrue(ownerRadioButton.isSelected(), "Owner radio button should be selected");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("text-danger")));
		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Role is required"));

		Assert.assertFalse(foundError, "Error message 'Role is required' should appear when Owner is not selected.");
	}

	@Test(description = "TC_017: Validate that Renter is chosen")
	void testRenterChosen() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement renterRadioButton = driver.findElement(By.id("renter"));
		renterRadioButton.click();

		wait.until(ExpectedConditions.elementToBeSelected(renterRadioButton));
		Assert.assertTrue(renterRadioButton.isSelected(), "Renter radio button should be selected");

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("text-danger")));
		List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Role is required"));

		Assert.assertFalse(foundError, "Error message 'Role is required' should appear when Owner is not selected.");
	}

	@Test(description = "TC_018: Validate register success")
	void testRegisterSuccess() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		usernameInput.sendKeys("Vũ Tùng Tâm");

		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailInput.sendKeys("test100452@example.com");

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
		phoneInput.sendKeys("0123456789");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		passwordInput.sendKeys("StrongPassword123");

		WebElement confirmPasswordField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("confirmPassword")));
		confirmPasswordField.sendKeys("StrongPassword123");

		WebElement renterRadioButton = driver.findElement(By.id("renter"));
		renterRadioButton.click();

		wait.until(ExpectedConditions.elementToBeSelected(renterRadioButton));

		WebElement signUpButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
		signUpButton.click();

		WebElement messageDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(text(), 'Welcome, Vũ Tùng Tâm')]")));
		
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
