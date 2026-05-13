import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;

public class Navigation {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //  Open Google
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        System.out.println("URL: " + driver.getCurrentUrl());
        Thread.sleep(2000);

        // Navigate to another site
        driver.navigate().to("https://www.example.com");
        System.out.println("Title: " + driver.getTitle());
        Thread.sleep(2000);

        //  Go back to Google
        driver.navigate().back();
        System.out.println("After back: " + driver.getTitle());
        Thread.sleep(2000);

        //  Go forward to example.com
        driver.navigate().forward();
        System.out.println("After forward: " + driver.getTitle());
        Thread.sleep(2000);

        // Refresh the page
        driver.navigate().refresh();
        System.out.println("After refresh: " + driver.getCurrentUrl());
        Thread.sleep(2000);

        driver.quit();
    }
}