package test_register;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;



public class RegisterTest {

    public WebDriver driver;

    @BeforeSuite
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\KT\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    void beginTestCase() {
        driver.get("https://www.youtube.com/");
    }
    
    @Test(description = "Verify valid full name input")
    void testValidFullName() {
        WebElement fullNameInput = driver.findElement(By.id("fullName"));
        fullNameInput.sendKeys("Nguyễn Văn A");
        driver.findElement(By.id("submitButton")).click();

        boolean isErrorDisplayed = driver.findElements(By.id("fullNameError")).size() > 0;
        Assert.assertFalse(isErrorDisplayed, "Thông báo lỗi không nên hiển thị");
    }

    @Test(description = "Verify empty full name input")
    void testEmptyFullName() {
        WebElement fullNameInput = driver.findElement(By.id("fullName"));
        fullNameInput.clear(); 
        driver.findElement(By.id("submitButton")).click();

        WebElement error = driver.findElement(By.id("fullNameError"));
        Assert.assertEquals(error.getText(), "Họ tên không được để trống");
    }

    @Test(description = "Kiểm tra họ tên dài hơn 255 ký tự")
    void testFullNameExceeds255() {
        WebElement fullNameInput = driver.findElement(By.id("fullName"));
        String longName = "A".repeat(256);
        fullNameInput.sendKeys(longName);
        driver.findElement(By.id("submitButton")).click();

        WebElement error = driver.findElement(By.id("fullNameError"));
        Assert.assertEquals(error.getText(), "Họ tên quá dài");
    }
    @AfterMethod
    void endTestCase() {
        
        driver.close();
    }

    @AfterSuite
    void tearDown() {
        
        if (driver != null) {
            driver.quit();
        }
    }
}
