package tests;

import model.UserProfile;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SignupTest extends TestBase{

    @Test
    public void testSignupFlow() {
        /*
        The demo follows "Test Case 1: Register User" on https://automationexercise.com/test_cases
        */

        UserProfile user = new UserProfile();

        /*
        1. Launch browser
        2. Navigate to url 'http://automationexercise.com'
        3. Verify that home page is visible successfully
        4. Click on 'Signup / Login' button
        */
        boolean isHomePageVisible = signup().toLoginPage();
        assertThat(isHomePageVisible).isTrue();

        /*
        5. Verify 'New User Signup!' is visible
        6. Enter name and email address
        */
        boolean isNewUserSignupVisible = signup().initiateSignupPage(user);
        assertThat(isNewUserSignupVisible).isTrue();

        /*
        7. Click 'Signup' button
        8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
        9. Fill details: Title, Name, Email, Password, Date of birth
        10. Select checkbox 'Sign up for our newsletter!'
        11. Select checkbox 'Receive special offers from our partners!'
        12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        */
        boolean isEnterAccountInfoVisible = signup().createAccount(user);
        assertThat(isEnterAccountInfoVisible).isTrue();

        /*
        13. Click 'Create Account button'
        14. Verify that 'ACCOUNT CREATED!' is visible
        15. Click 'Continue' button
        */
        boolean isAccountCreatedVisible = signup().toLoggedInAsUsername();
        assertThat(isAccountCreatedVisible).isTrue();

        // 16. Verify that 'Logged in as username' is visible
        boolean isCurrentUserInfoMatch = signup().isCurrentUserInfoMatch(user);
        assertThat(isCurrentUserInfoMatch).isTrue();

        /*
        17. Click 'Delete Account' button
        18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        */
        boolean isAccountDeleted = signup().deleteAccount();
        assertThat(isAccountDeleted).isTrue();

    }

}
