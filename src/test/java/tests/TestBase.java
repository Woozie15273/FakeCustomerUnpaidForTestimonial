package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import pages.SignupPage;
import utility.DriverFactory;

public abstract class TestBase {
    protected WebDriver driver;

    /** Page Factory */
    private SignupPage signup;

    public SignupPage signup() {
        return (signup == null) ? (signup = new SignupPage(driver)) : signup;
    }

    /** Driver Factory*/
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

}
