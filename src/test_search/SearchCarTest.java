package search_test;

import java.time.Duration;

import org.openqa.selenium.By;
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
import java.util.List;

public class SearchCarTest {
	private WebDriver driver;

	@BeforeSuite
	void setUp() {
		try {
			System.setProperty("webdriver.chrome.driver", "D:\\KT\\chromedriver-win64\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			driver.get("http://localhost:3000");
			WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in")));
			loginLink.click();
			WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
			emailInput.sendKeys("dophuong23082003@gmail.com");

			WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
			passwordInput.sendKeys("abc123456");

			WebElement logInButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
			logInButton.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize WebDriver. Check ChromeDriver setup.");
		}
	}

	@BeforeMethod
	void beginTestCase() {
	}

	@AfterMethod
	void afterTestCase() {
		try {
			driver.get("http://localhost:3000");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(description = "TC_001: Verify empty location input")
	void testEmptyLocation() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement locationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickUpLocation")));
		locationInput.clear();
		locationInput.sendKeys("");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("locationErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Please provide a pick-up location!"));

		Assert.assertTrue(foundError, "Error message should appear for empty location input.");
	}

	@Test(description = "TC_002: Verify not empty location input")
	void testNotEmptyLocation() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement locationInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("pickUpLocation")));
		locationInput.clear();
		locationInput.sendKeys("Hanoi");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("locationErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Please provide a pick-up location!"));

		Assert.assertFalse(foundError, "Error message should not appear for valid location input.");
	}

	@Test(description = "TC_003: Verify empty pick-up date input")
	void testEmptyPickUpDate() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement pickUpDateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("pickUpDate")));
		pickUpDateInput.clear();

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("pickUpDateErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Pick-up date is required!"));

		Assert.assertTrue(foundError, "Error message should appear for empty pick-up date input.");
	}

	@Test(description = "TC_004: Verify not empty pick-up date input")
	void testNotEmptyPickUpDate() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement pickUpDateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("pickUpDate")));
		pickUpDateInput.clear();
		pickUpDateInput.sendKeys("31/12/2024");
		
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("pickUpDateErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Pick-up time is required!"));

		Assert.assertFalse(foundError, "Error message should not appear for valid pick-up date input.");
	}

	@Test(description = "TC_005: Verify empty drop-off date input")
	void testEmptyDropOffDate() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement dropOffDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropOffDate")));
		dropOffDateInput.clear();

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("dropOffDateErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Drop-off date is required!"));

		Assert.assertTrue(foundError, "Error message should appear for empty drop-off date input.");
	}

	@Test(description = "TC_006: Verify not empty drop-off date input")
	void testNotEmptyDropOffDate() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement dropOffDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropOffDate")));
		dropOffDateInput.clear();
		dropOffDateInput.sendKeys("31/12/2024");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("dropOffDateErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Drop-off date is required!"));

		Assert.assertFalse(foundError, "Error message should not appear for valid drop-off date input.");
	}

	@Test(description = "TC_007: Verify pick-up date and time before now")
	void testPickUpBeforeNow() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement pickUpDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickUpDate")));
		pickUpDateInput.clear();
		pickUpDateInput.sendKeys("22/12/2024");

		WebElement searchButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("pickUpDateErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Pick-up Date and Time must be greater than now!"));

		Assert.assertTrue(foundError,
				"Error message 'Pick-up Date and Time must be greater than now!' should appear");
	}

	@Test(description = "TC_008: Verify pick-up date and time after now")
	void testPickUpAfterNow() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement pickUpDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickUpDate")));
		pickUpDateInput.clear();
		pickUpDateInput.sendKeys("30/12/2024");

		WebElement searchButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("pickUpDateEr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Pick-up Date and Time must be greater than now!"));

		Assert.assertFalse(foundError,
				"Error message 'Pick-up Date and Time must be greater than now!' should not appear");
	}

	@Test(description = "TC_009: Verify drop-off date and time before pick-up date and time")
	void testDropOffBeforePickUp() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement locationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickUpLocation")));
		locationInput.clear();
		locationInput.sendKeys("Hanoi");

		WebElement pickUpDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickUpDate")));
		pickUpDateInput.clear();
		pickUpDateInput.sendKeys("30/12/2024");

		WebElement dropOffDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropOffDate")));
		dropOffDateInput.clear();
		dropOffDateInput.sendKeys("29/12/2024");

		WebElement searchButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("dropOffDateErr"));
		boolean foundError = errorMessages.stream().anyMatch(
				error -> error.getText().equals("Drop-off Date and Time must be greater than Pick-up Date and Time!"));

		Assert.assertTrue(foundError,
				"Error message 'Drop-off Date and Time must be greater than Pick-up Date and Time!' should appear");
	}

	@Test(description = "TC_010: Verify drop-off date and time after pick-up date and time")
	void testDropOffAfterPickUp() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement pickUpDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickUpDate")));
		pickUpDateInput.clear();
		pickUpDateInput.sendKeys("30/12/2024");

		WebElement dropOffDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropOffDate")));
		dropOffDateInput.clear();
		dropOffDateInput.sendKeys("31/12/2024");

		WebElement searchButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("dropOffDateErr"));
		boolean foundError = errorMessages.stream().anyMatch(
				error -> error.getText().equals("Drop-off Date and Time must be greater than Pick-up Date and Time!"));

		Assert.assertFalse(foundError,
				"Error message 'Drop-off Date and Time must be greater than Pick-up Date and Time!' should not appear");
	}

	@Test(description = "TC_011: Test search cars success")
	void testSearchCarSuccess() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement pickUpDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickUpDate")));
		pickUpDateInput.clear();
		pickUpDateInput.sendKeys("30/12/2024");

		WebElement dropOffDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropOffDate")));
		dropOffDateInput.clear();
		dropOffDateInput.sendKeys("31/12/2024");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
		searchButton.click();
		
		WebElement searchResultsHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h4[text()='Search Results']")));

	    Assert.assertNotNull(searchResultsHeader, "Search Results header should be displayed after search.");
	}

	@AfterSuite
	void tearDown() {
		driver.quit();
	}
}
