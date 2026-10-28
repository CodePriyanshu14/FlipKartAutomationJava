package pages;

import models.FlipkartTestData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage extends BasePage {

    private final By upiOption =
            By.xpath("//*[normalize-space()='UPI']");

    private final By cardOption =
            By.xpath("//*[normalize-space()='Credit / Debit / ATM Card']");

    private final By emiOption =
            By.xpath("//*[normalize-space()='EMI']");

    private final By netBankingOption =
            By.xpath("//*[normalize-space()='Net Banking']");

    private final By giftCardOption =
            By.xpath("//*[contains(normalize-space(),'Flipkart Gift Card')]");

    private final By cashOnDeliveryOption =
            By.xpath("//*[normalize-space()='Cash on Delivery']");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void selectPaymentMethod(FlipkartTestData testData) {

        String paymentMethod = testData.getPaymentMethod();

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Payment method is missing from Excel test data."
            );
        }

        System.out.println();
        System.out.println("========== PAYMENT ==========");
        System.out.println("Selecting payment method: " + paymentMethod);

        switch (paymentMethod.trim().toLowerCase()) {

            case "upi":
                selectPaymentOption(upiOption, "UPI");
                break;

            case "card":
            case "credit/debit/atm card":
            case "credit / debit / atm card":
                selectPaymentOption(
                        cardOption,
                        "Credit / Debit / ATM Card"
                );
                break;

            case "emi":
                selectPaymentOption(emiOption, "EMI");
                break;

            case "net banking":
                selectPaymentOption(
                        netBankingOption,
                        "Net Banking"
                );
                break;

            case "gift card":
            case "flipkart gift card":
                selectPaymentOption(
                        giftCardOption,
                        "Flipkart Gift Card"
                );
                break;

            case "cash on delivery":
            case "cod":
                selectPaymentOption(
                        cashOnDeliveryOption,
                        "Cash on Delivery"
                );
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported payment method: " + paymentMethod
                );
        }

        hardWait(1500);

        System.out.println(
                "Payment method selected successfully: "
                        + paymentMethod
        );
    }

    private void selectPaymentOption(
            By locator,
            String paymentMethod) {

        waitForClickable(locator);
        scrollTo(locator);
        jsClick(locator);

        System.out.println(
                "Selected payment option: " + paymentMethod
        );
    }

    public boolean isPaymentPageDisplayed() {

        return isPresent(upiOption)
                || isPresent(cardOption)
                || isPresent(emiOption)
                || isPresent(netBankingOption);
    }
}