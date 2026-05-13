package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;

    String URL      = "https://the-internet.herokuapp.com/login";
    String VALID_USER = "tomsmith";
    String VALID_PASS = "SuperSecretPassword!";

    // Runs before every @Test method
    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(URL);
        loginPage = new LoginPage(driver);
    }

    // Runs after every @Test method
    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    // TC01 — Valid username and valid password

    public void testValidLogin()  {
        loginPage.login(VALID_USER, VALID_PASS);
        String msg = loginPage.getSuccessMessage();
        Assert.assertTrue(msg.contains("You logged into a secure area"),
                "TC01 FAILED: Success message not found");
        System.out.println("TC01 PASSED: Valid login successful");
    }

    // TC02 — Valid username + wrong password

    @Test
    public void testInvalidPassword() {
        loginPage.login(VALID_USER, "wrongpassword");
        String msg = loginPage.getErrorMessage();
        Assert.assertTrue(msg.contains("Your password is invalid"),
                "TC02 FAILED: Error message not shown");
        System.out.println("TC02 PASSED: Invalid password handled correctly");
    }

    // TC03 — Wrong username + valid password

    @Test
    public void testInvalidUsername() {
        loginPage.login("wronguser", VALID_PASS);
        String msg = loginPage.getErrorMessage();
        Assert.assertTrue(msg.contains("Your username is invalid"),
                "TC03 FAILED: Error message not shown");
        System.out.println("TC03 PASSED: Invalid username handled correctly");
    }


    // TC04 — Both fields empty

    @Test
    public void testEmptyCredentials() {
        loginPage.login("", "");
        String msg = loginPage.getErrorMessage();
        Assert.assertFalse(msg.isEmpty(),
                "TC04 FAILED: No error shown for empty fields");
        System.out.println("TC04 PASSED: Empty fields handled correctly");
    }

    // TC05 — Only username, password empty

    @Test
    public void testEmptyPassword() {
        loginPage.login(VALID_USER, "");
        String msg = loginPage.getErrorMessage();
        Assert.assertFalse(msg.isEmpty(),
                "TC05 FAILED: No error for empty password");
        System.out.println("TC05 PASSED: Empty password handled correctly");
    }

    // TC06 — Only password, username empty

    @Test
    public void testEmptyUsername() {
        loginPage.login("", VALID_PASS);
        String msg = loginPage.getErrorMessage();
        Assert.assertFalse(msg.isEmpty(),
                "TC06 FAILED: No error for empty username");
        System.out.println("TC06 PASSED: Empty username handled correctly");
    }


    // TC07 — SQL Injection attempt

    @Test
    public void testSQLInjection() {
        loginPage.login("' OR 1=1 --", "anything");
        String msg = loginPage.getErrorMessage();
        Assert.assertFalse(msg.isEmpty(),
                "TC07 FAILED: SQL injection bypassed login!");
        System.out.println("TC07 PASSED: SQL injection blocked correctly");
    }


    // TC08 — Password field should be masked

    @Test
    public void testPasswordIsMasked() {
        Assert.assertTrue(loginPage.isPasswordMasked(),
                "TC08 FAILED: Password field is not masked!");
        System.out.println("TC08 PASSED: Password field is masked");
    }
}