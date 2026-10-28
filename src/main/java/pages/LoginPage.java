package pages;

import models.FlipkartTestData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By loginButton = By.xpath(
            "//span[normalize-space()='Login']" +
            " | //a[normalize-space()='Login']" +
            " | //a[contains(@href,'/account/login')]"
    );

    private final By mobileNumberTextBox =
            By.xpath("//input[@type='number']");

    private final By requestOtpButton = By.xpath(
            "//button[normalize-space()='Continue']"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(FlipkartTestData testData) {

        System.out.println("Checking login status...");

        if (!isLoginRequired()) {
            System.out.println("Already Logged In.");
            return;
        }

        System.out.println("Login Required.");

        openLoginPopup();

        enterMobileNumber(testData.getMobileNumber());

        requestOtp();

        waitForManualOtp();
    }

    public boolean isLoginRequired() {

        try {
            return isPresent(loginButton);
        } catch (Exception e) {
            return false;
        }
    }

    private void openLoginPopup() {

        System.out.println("Opening Login Popup...");

        if (isPresent(mobileNumberTextBox)) {
            System.out.println("Login popup is already open.");
            return;
        }

        waitForClickable(loginButton);

        jsClick(loginButton);

        waitForElement(mobileNumberTextBox);

        System.out.println("Login popup opened.");
    }

    private void enterMobileNumber(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mobile number is missing from Excel test data."
            );
        }

        System.out.println("Entering mobile number...");

        clear(mobileNumberTextBox);
        enterText(mobileNumberTextBox, mobileNumber);
    }

    private void requestOtp() {

        System.out.println("Requesting OTP...");

        waitForClickable(requestOtpButton);
        jsClick(requestOtpButton);

        System.out.println("OTP requested.");
    }

    private void waitForManualOtp() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("OTP LOGIN REQUIRED");
        System.out.println("Complete OTP verification manually.");
        System.out.println("Waiting for login to complete...");
        System.out.println("========================================");

        int timeoutSeconds = 60;

        for (int i = 0; i < timeoutSeconds; i++) {

            if (!isLoginRequired()) {
                System.out.println("Login Successful.");
                return;
            }

            hardWait(1000);
        }

        throw new AssertionError(
                "Login was not completed within "
                        + timeoutSeconds
                        + " seconds."
        );
    }
}