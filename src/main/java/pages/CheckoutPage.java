package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final By checkoutPageContent =
            By.xpath("//*[normalize-space()='Deliver to:']");

    private final By continueButton =
            By.xpath("//*[normalize-space()='Continue']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCheckoutLoaded() {

        System.out.println("Verifying Checkout page...");

        waitForPage();
        hardWait(2000);

        if (isPresent(checkoutPageContent)) {
            System.out.println("Checkout page loaded successfully.");
        } else {
            throw new AssertionError(
                    "Checkout page was not loaded. Expected 'Deliver to:' section was not found."
            );
        }
    }

    public void clickContinue() {

        System.out.println("Clicking Continue on Checkout page...");

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {

                System.out.println(
                        "Continue button attempt: " + attempt
                );

                // Re-locate the element every time.
                waitForClickable(continueButton);

                // Re-locate again immediately before scrolling.
                scrollTo(continueButton);

                // Re-locate again immediately before clicking.
                jsClick(continueButton);

                System.out.println(
                        "Continue clicked successfully."
                );

                waitForPage();
                hardWait(3000);

                System.out.println(
                        "Current URL: " + driver.getCurrentUrl()
                );

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Continue button became stale. Retrying..."
                );

                hardWait(1000);
            }
        }

        throw new AssertionError(
                "Unable to click Continue button after "
                        + maxAttempts
                        + " attempts because the element kept becoming stale."
        );
    }

    public void verifyPaymentPageLoaded() {

        System.out.println("Verifying Payment page...");

        waitForPage();
        hardWait(2000);

        System.out.println(
                "Payment Page URL: " + driver.getCurrentUrl()
        );

        System.out.println(
                "Payment Page Title: " + driver.getTitle()
        );

        System.out.println();
        System.out.println(
                "========== PAYMENT PAGE TEXT =========="
        );

        String pageText =
                driver.findElement(By.tagName("body")).getText();

        if (pageText == null || pageText.trim().isEmpty()) {

            throw new AssertionError(
                    "No visible payment page content was detected."
            );
        }

        System.out.println(pageText);

        System.out.println(
                "========== END PAYMENT PAGE TEXT =========="
        );

        System.out.println();

        captureScreenshot("PaymentPage");
    }
}