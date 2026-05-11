package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    // --- Locators ---
    By usernameField  = By.id("username");
    By passwordField  = By.id("password");
    By loginButton    = By.cssSelector("button[type='submit']");
    By successMessage = By.cssSelector(".flash.success");
    By errorMessage   = By.cssSelector(".flash.error");

    // --- Constructor ---
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Actions ---
    public void enterUsername(String username) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameField)
        );
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // --- Reusable login method ---
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // --- Result readers ---
    public String getSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMessage)
        ).getText();
    }

    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }

    public boolean isPasswordMasked() {
        String type = driver.findElement(passwordField).getAttribute("type");
        return type.equals("password");  // "password" means it is masked
    }

    public boolean isForgotPasswordLinkPresent() {
        return driver.findElements(By.linkText("Forgot Password")).size() > 0;
    }
}