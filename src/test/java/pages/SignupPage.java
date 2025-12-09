package pages;

import model.UserProfile;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import utility.Util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import net.datafaker.Faker;

import java.util.List;
import java.util.Random;

public class SignupPage extends BasePage {

    public SignupPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@href='/delete_account']")
    private WebElement deleteAccountBtn;

    /** Login page */
    @FindBy(xpath = "//form[@action='/login']//input[@type='email']")
    private WebElement loginEmail;

    @FindBy(xpath = "//form[@action='/login']//input[@type='password']")
    private WebElement loginPwd;

    @FindBy(xpath = "//form[@action='/login']//button[@type='submit']")
    private WebElement loginBtn;

    /** Reused by both /login and /signup */
    @FindBy(xpath = "//form[@action='/signup']//input[@name='name']")
    private WebElement signupName;

    @FindBy(xpath = "//form[@action='/signup']//input[@type='email']")
    private WebElement signupEmail;

    @FindBy(xpath = "//form[@action='/signup']//button[@type='submit']")
    private WebElement signupBtn;

    /** Signup page */
    @FindBy(xpath = "//input[@name='title']")
    private List<WebElement> titles;

    private WebElement signupPwd() {
        return findById("password");
    }

    private WebElement dobDay() {
        return findById("days");
    }

    private WebElement dobMonth() {
        return findById("months");
    }

    private WebElement dobYear() {
        return findById("years");
    }

    private WebElement newsletterCheckbox() {
        return findById("newsletter");
    }

    private WebElement offerCheckbox() {
        return findById("optin");
    }

    private WebElement firstName() {
        return findById("first_name");
    }

    private WebElement lastName() {
        return findById("last_name");
    }

    private WebElement company() {
        return findById("company");
    }

    private WebElement address1() {
        return findById("address1");
    }

    private WebElement address2() {
        return findById("address2");
    }

    private WebElement country() {
        return findById("country");
    }

    private WebElement state() {
        return findById("state");
    }

    private WebElement city() {
        return findById("city");
    }

    private WebElement zipcode() {
        return findById("zipcode");
    }

    private WebElement mobileNumber() {
        return findById("mobile_number");
    }

    /** Others */
    @FindBy(xpath = "//a[@data-qa='continue-button']")
    private WebElement continueBtn;

    @FindBy(xpath = "//i[contains(@class,'fa-user')]/..")
    private WebElement currentUser;

    private boolean execIfVisible(By locator, Runnable action) {
        if (!waitForElementVisibility(locator)) {
            return false;
        }
        action.run();
        return true;
    }

    public boolean toLoginPage() {
        By loginNavigation = By.xpath("//a[@href='/login']");
        driver.get(Util.getWebsite());
        return execIfVisible(loginNavigation, () -> {
            driver.findElement(loginNavigation).click();
        });
    }

    public boolean initiateSignupPage(UserProfile user) {
        By text = By.xpath("//*[text()='New User Signup!']");
        return execIfVisible(text, () -> {
            signupName.sendKeys(user.getName());
            signupEmail.sendKeys(user.getEmail());
        });
    }

    public boolean createAccount(UserProfile user) {
        signupBtn.click();

        By text = By.xpath("//*[text()='Enter Account Information']");
        return execIfVisible(text, () -> {
            enterAccountInfo(user);
            enterAddressInfo(user);
        });
    }

    private void enterAccountInfo(UserProfile user) {
        WebElement randomTitle = titles.get(new Random().nextInt(titles.size()));
        randomTitle.click();

        signupPwd().sendKeys(user.getPassword());

        selectDOB(user.getDob());

        newsletterCheckbox().click();
        offerCheckbox().click();
    }

    private void selectDOB(String dob) {
        String[] separateDate = separateString(dob, "-"); // e.g. 1999-12-08
        String y = separateDate[0];
        String m = formatDate(separateDate[1]);
        String d = formatDate(separateDate[2]);

        selectDropdownOption(dobYear(), y);
        selectDropdownOption(dobMonth(), formatDate(m));
        selectDropdownOption(dobDay(), d);
    }

    // Helper: Remove leading zero from 01 to 09
    private String formatDate(String s) {
        if (s.startsWith("0")) {
            return s.substring(1); // e.g. "08" -> "8"
        }
        return s;
    }

    private void enterNameInSplit(String fullName) {
        String[] separateName = separateString(fullName, "\\s+");
        String f = separateName[0];
        String l = separateName[1];

        firstName().sendKeys(f);
        lastName().sendKeys(l);
    }

    private void enterAddressInSplit(String fullAddress) {
        String[] separateAddress = separateString(fullAddress, ",");
        String[] stateZip = separateString(separateAddress[2].trim(), " ");

        String address = separateAddress[0];
        String city = separateAddress[1];
        String state = stateZip[0];
        String zipcode = stateZip[1];

        address1().sendKeys(address);
        city().sendKeys(city);
        state().sendKeys(state);
        zipcode().sendKeys(zipcode);

    }

    private String[] separateString(String s, String regex) {
        return s.trim().split(regex);
    }

    private void enterAddressInfo(UserProfile user) {
        Faker faker = new Faker();

        enterNameInSplit(user.getName());

        company().sendKeys(faker.company().name());

        enterAddressInSplit(user.getAddress());

        // Force to select US due to data localization restrictions
        selectDropdownOption(country(), "United States");

        mobileNumber().sendKeys(faker.phoneNumber().phoneNumber());
    }

    public boolean toLoggedInAsUsername() {
        signupBtn.click();

        By text = By.xpath("//*[text()='Account Created!']");
        return execIfVisible(text, () -> {
            continueBtn.click();
            waitForElementVisibility(currentUser);
        });
    }

    public boolean isCurrentUserInfoMatch(UserProfile user) {
        String username = getDomProperty(currentUser, "innerText").trim();
        return username.contains(user.getName());
    }

    public boolean deleteAccount() {
        deleteAccountBtn.click();

        By text = By.xpath("//*[text()='Account Deleted!']");
        return execIfVisible(text, () -> {
            continueBtn.click();
        });
     }

}
