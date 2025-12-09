package utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static WebDriver driver;
    private static final boolean headless = Util.getHeadless();

    /** If failed to download chromedriver like being blocked by GFW, redirect to the driver under /test/resources
    * */
    public static WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            if (headless) options.addArguments("--headless=new","--disable-gpu","--window-size=1920,1080");

            try {
                WebDriverManager.chromedriver().setup();
            } catch (Exception e) {
                if (e.getCause() instanceof java.net.SocketException) {
                    System.setProperty("webdriver.chrome.driver",
                            System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
                } else throw e;
            }

            driver = new ChromeDriver(options);
            if (!headless) driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
