package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    private final Duration defaultInterval = Duration.ofSeconds(10);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement findById(String id) {
        return driver.findElement(By.id(id));
    }

    public String getDomProperty(WebElement e, String propName) {
        return e.getDomProperty(propName);
    }

    public void selectDropdownOption(WebElement e, String value) {
        new Select(e).selectByValue(value);
    }

    public boolean waitForElementVisibility(WebElement element) {
        try {
            new WebDriverWait(driver, defaultInterval)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean waitForElementVisibility(By locator) {
        try {
            new WebDriverWait(driver, defaultInterval)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
