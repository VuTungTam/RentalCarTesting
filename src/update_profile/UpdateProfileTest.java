package update_profile;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class UpdateProfileTest {
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
			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.className("img-profile")));
			profile.click();

			WebElement profileButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='My profile']")));
			profileButton.click();
			WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Edit']")));

			// Click bằng JavaScript
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize WebDriver. Check ChromeDriver setup.");
		}
	}

	@BeforeMethod
	void beginTestCase() {

	}

	@Test(description = "TC_001: Verify empty full name input")
    public void testFullNameEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
	    usernameInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Full name is required"));

		Assert.assertTrue(foundError, "Error message 'Full name is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_002: Verify empty phone number input")
    public void testPhoneNumberEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement phoneInput = driver.findElement(By.name("phoneNumber"));
	    phoneInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Phone number is required"));

		Assert.assertTrue(foundError, "Error message 'Phone number is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_003: Verify empty citized id input")
    public void testCitizenIdEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement citizenIdInput = driver.findElement(By.name("citizenId"));
	    citizenIdInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Citizen ID is required"));

		Assert.assertTrue(foundError, "Error message 'Citizen ID is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_004: Verify empty birthday input")
    public void testBirthdayEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement birthdayInput = driver.findElement(By.name("birthday"));
	    birthdayInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Birthday is is required"));

		Assert.assertFalse(foundError, "Error message 'Birthday is is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_005: Verify empty driving license input")
    public void testDrivingLicenseEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement drivingLicenseInput = driver.findElement(By.name("drivingLicense"));
	    drivingLicenseInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Driving license is required"));

		Assert.assertTrue(foundError, "Error message 'Driving license is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_006: Verify empty city input")
    public void testCityEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement cityInput = driver.findElement(By.name("city"));
	    cityInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("City is required"));

		Assert.assertTrue(foundError, "Error message 'City is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_007: Verify empty distric input")
    public void testDistrictEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement districtInput = driver.findElement(By.name("district"));
	    districtInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("District is required"));

		Assert.assertTrue(foundError, "Error message 'District is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_008: Verify empty ward input")
    public void testWardEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement wardInput = driver.findElement(By.name("ward"));
	    wardInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Ward is required"));

		Assert.assertTrue(foundError, "Error message 'Ward is required' should appear when Owner is not selected.");
    }
	@Test(description = "TC_009: Verify empty street input")
    public void testStreetEmpty() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement streetInput = driver.findElement(By.name("street"));
	    streetInput.clear();

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

        List<WebElement> errorMessages = driver.findElements(By.className("text-danger"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Street\\House number is required"));

		Assert.assertTrue(foundError, "Error message 'Street\\House number is required' should appear when Owner is not selected.");
    }
	
	@Test(description = "TC_010: Verify discard button function")
	public void testDiscardButtonFunctionality() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    // Click nút Discard
	    WebElement discardButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-danger') and text()='Discard']")));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", discardButton);
	    
	    WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Edit']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
	}
	
	@Test(description = "TC_011: Verify update profile")
	public void testUpdateProfileWithValidData() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Điền dữ liệu vào các trường input dựa trên thuộc tính name
	    WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
	    usernameInput.clear();
	    usernameInput.sendKeys("Tung Tam");

	    WebElement phoneInput = driver.findElement(By.name("phoneNumber"));
	    phoneInput.clear();
	    phoneInput.sendKeys("0987654321");

	    WebElement citizenIdInput = driver.findElement(By.name("citizenId"));
	    citizenIdInput.clear();
	    citizenIdInput.sendKeys("123456789");

	    WebElement birthdayInput = driver.findElement(By.name("birthday"));
	    birthdayInput.clear();
	    birthdayInput.sendKeys("2000-01-01");

	    WebElement drivingLicenseInput = driver.findElement(By.name("drivingLicense"));
	    drivingLicenseInput.clear();
	    drivingLicenseInput.sendKeys("DL123456");

	    WebElement cityInput = driver.findElement(By.name("city"));
	    cityInput.clear();
	    cityInput.sendKeys("Hanoi");

	    WebElement districtInput = driver.findElement(By.name("district"));
	    districtInput.clear();
	    districtInput.sendKeys("Cau Giay");

	    WebElement wardInput = driver.findElement(By.name("ward"));
	    wardInput.clear();
	    wardInput.sendKeys("Dich Vong");

	    WebElement streetInput = driver.findElement(By.name("street"));
	    streetInput.clear();
	    streetInput.sendKeys("123 ABC Street");

		WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Save']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);

		// Kiểm tra nếu cập nhật thành công (giả sử có thông báo thành công)
		WebElement messageDiv = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Update User Successfull')]")));
		Assert.assertTrue(messageDiv.isDisplayed(), "Toast should appear");

		System.out.println("Update successful: " + messageDiv.getText());
		
		WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-success') and text()='Edit']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
	}

	@AfterMethod
	void endTestCase() {
		// Không đóng trình duyệt tại đây để tránh mất session.
	}

	@AfterSuite
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
