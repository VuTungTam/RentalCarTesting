package update_car;

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

public class UpdateCarTest {
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
			emailInput.sendKeys("bido23082003@gmail.com");

			WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
			passwordInput.sendKeys("abc123456");

			WebElement submitBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
			submitBtn.click();

			WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.className("img-profile")));
			profile.click();

			WebElement carsButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='My cars']")));
			carsButton.click();

			WebElement firstLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-details")));
			firstLink.click();
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
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, 0);");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(description = "TC_001: Verify empty mileage input")
	void testEmptyMileage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mileage")));
		input.clear();

		WebElement updateButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("updateBtn")));
		updateButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("mileageErr"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Mileage is required!"));

		Assert.assertTrue(foundError,
				"Error message 'Mileage is required!' should appear when a valid mileage is not provided.");
	}

	@Test(description = "TC_002: Verify not empty mileage input")
	void testNotEmptyMileage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement mileageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mileage")));
		mileageInput.clear();
		mileageInput.sendKeys("100");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("mileageErr"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Mileage is required!"));

		Assert.assertFalse(foundError,
				"Error message 'Mileage is required!' should not appear when a valid mileage is provided.");
	}

	@Test(description = "TC_003: Verify empty fuel comsumption input")
	void testEmptyFuelConsumption() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement fuelConsumptionInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("fuelConsumption")));
		fuelConsumptionInput.clear();

		WebElement updateButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("updateBtn")));
		updateButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("fuelConsumptionErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Fuel consumption is required!"));

		Assert.assertTrue(foundError,
				"Error message 'Fuel consumption is required!' should appear when a valid fuel comsumption is not provided.");
	}

	@Test(description = "TC_004: Verify not empty fuel comsumption input")
	void testNotEmptyFuelConsumption() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement fuelConsumptionInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("fuelConsumption")));
		fuelConsumptionInput.clear();
		fuelConsumptionInput.sendKeys("8");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("fuelConsumptionErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Fuel consumption is required!"));

		Assert.assertFalse(foundError,
				"Error message 'Fuel consumption is required!' should not appear when a valid fuel comsumption is provided.");
	}

	@Test(description = "TC_005: Verify empty city/province input")
	void testEmptyCity() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city")));
		input.clear();

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("cityErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("City/Province is required!"));

		Assert.assertTrue(foundError,
				"Error message 'City/Province is required!' should appear when a valid city/province is not provided.");
	}

	@Test(description = "TC_006: Verify not empty city/province input")
	void testNotEmptyCity() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement cityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city")));
		cityInput.clear();
		cityInput.sendKeys("Hanoi");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("cityErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("City/Province is required!"));

		Assert.assertFalse(foundError,
				"Error message 'City/Province is required!' should not appear when a valid city/province is provided.");
	}

	@Test(description = "TC_007: Verify empty district input")
	void testEmptyDistrict() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("district")));
		input.clear();
		;

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("districtErr"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("District is required!"));

		Assert.assertTrue(foundError,
				"Error message 'District is required!' should appear when a valid district is not provided.");
	}

	@Test(description = "TC_008: Verify not empty district input")
	void testNotEmptyDistrict() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement districtInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("district")));
		districtInput.clear();
		districtInput.sendKeys("Cau Giay");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("districtErr"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("District is required!"));

		Assert.assertFalse(foundError,
				"Error message 'District is required!' should not appear when a valid district is provided.");
	}

	@Test(description = "TC_009: Verify empty ward input")
	void testEmptyWard() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ward")));
		input.clear();

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("wardErr"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Ward is required!"));

		Assert.assertTrue(foundError,
				"Error message 'Ward is required!' should appear when a valid ward is not provided.");
	}

	@Test(description = "TC_010: Verify not empty ward input")
	void testNotEmptyWard() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement wardInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ward")));
		wardInput.clear();
		wardInput.sendKeys("Dich Vong");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("wardErr"));
		boolean foundError = errorMessages.stream().anyMatch(error -> error.getText().equals("Ward is required!"));

		Assert.assertFalse(foundError,
				"Error message 'Ward is required!' should not appear when a valid ward is provided.");
	}

	@Test(description = "TC_011: Verify empty house number/street input")
	void testEmptyStreet() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("houseNumber")));
		input.clear();

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("streetErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("House number/Street is required!"));

		Assert.assertTrue(foundError,
				"Error message 'House number/Street is required!' should appear when a valid house number/street is not provided.");
	}

	@Test(description = "TC_012: Verify not empty house number/street input")
	void testNotEmptyStreet() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement streetInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("houseNumber")));
		streetInput.clear();
		streetInput.sendKeys("123 Le Van Luong");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updateBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("streetErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("House number/Street is required!"));

		Assert.assertFalse(foundError,
				"Error message 'House number/Street is required!' should not appear when a valid house number/street is provided.");
	}

	@Test(description = "TC_013: Verify empty base price input")
	void testEmptyPrice() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement pricingTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("pricingTab")));
		pricingTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("basePrice")));
		input.clear();

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updatePriceBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("priceErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Base price is required!"));

		Assert.assertTrue(foundError,
				"Error message 'Base price is required!' should appear when a valid base price is not provided.");
	}

	@Test(description = "TC_014: Verify not empty base price input")
	void testNotEmptyPrice() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement pricingTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("pricingTab")));
		pricingTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement basePriceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("basePrice")));
		basePriceInput.clear();
		basePriceInput.sendKeys("500000");

		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("updatePriceBtn")));
		searchButton.click();

		List<WebElement> errorMessages = driver.findElements(By.id("priceErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("Base price is required!"));

		Assert.assertFalse(foundError,
				"Error message 'Base price is required!' should not appear when a valid base price is provided.");
	}

	@Test(description = "TC_015: Test update car success")
	void testUpdateCarSuccess() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("detailsTab")));
		detailsTab.click();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement streetInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("houseNumber")));
		streetInput.clear();
		streetInput.sendKeys("123 Le Van Luong");

		List<WebElement> errorMessages = driver.findElements(By.id("streetErr"));
		boolean foundError = errorMessages.stream()
				.anyMatch(error -> error.getText().equals("House number/Street is required!"));

		Assert.assertFalse(foundError,
				"Error message 'House number/Street is required!' should not appear when a valid house number/street is provided.");
	}

	@AfterSuite
	void tearDown() {
		driver.quit();
	}
}
