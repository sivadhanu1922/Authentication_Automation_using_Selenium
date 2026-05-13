package Tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class AlertDemo {

    WebDriver driver;
    WebDriverWait wait;
    String URL = "https://the-internet.herokuapp.com/javascript_alerts";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    // Test 1 — Simple Alert

    @Test(priority = 1)
    public void testSimpleAlert() throws InterruptedException {
        driver.get(URL);

        // Click button to trigger simple alert
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']"))
                .click();

        // Wait for alert to appear
        wait.until(ExpectedConditions.alertIsPresent());

        // Switch to alert
        Alert alert = driver.switchTo().alert();

        // Read and verify message
        String message = alert.getText();
        System.out.println("Simple Alert Message: " + message);
        Assert.assertEquals(message, "I am a JS Alert",
                "Alert message mismatch");

        // Click OK
        alert.accept();
        Thread.sleep(1000);

        // Verify result text on page
        String result = driver.findElement(By.id("result")).getText();
        Assert.assertTrue(result.contains("You successfully clicked an alert"),
                "Result message mismatch");

        System.out.println("TC01 PASSED: Simple alert handled");
    }

    // Test 2 — Confirm Alert — Click OK

    @Test(priority = 2)
    public void testConfirmAlertAccept() throws InterruptedException {
        driver.get(URL);

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']"))
                .click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        // Read message
        String message = alert.getText();
        System.out.println("Confirm Alert Message: " + message);
        Assert.assertEquals(message, "I am a JS Confirm",
                "Confirm message mismatch");

        alert.accept();
        Thread.sleep(1000);

        String result = driver.findElement(By.id("result")).getText();
        Assert.assertTrue(result.contains("Ok"),
                "OK not reflected in result");

        System.out.println("TC02 PASSED: Confirm alert accepted");
    }

    // Test 3 — Prompt Alert

    @Test(priority = 3)
    public void testPromptAlert() throws InterruptedException {
        driver.get(URL);

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']"))
                .click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        String message = alert.getText();
        System.out.println("Prompt Alert Message: " + message);
        Assert.assertEquals(message, "I am a JS prompt",
                "Prompt message mismatch");

        // Type into prompt
        alert.sendKeys("Siva");

        alert.accept();
        Thread.sleep(1000);

        String result = driver.findElement(By.id("result")).getText();
        Assert.assertTrue(result.contains("Siva"),
                "Input not reflected in result");

        System.out.println("TC04 PASSED: Prompt alert handled with input");
    }
}