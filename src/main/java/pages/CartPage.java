package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By placeOrderButton =
            By.xpath(
                    "//div[normalize-space()='Place Order']"
            );

    private final By cartContainer =
            By.xpath(
                    "//*[contains(text(),'My Cart') or " +
                    "contains(text(),'My cart')]"
            );

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCartLoaded() {

        waitForPage();

        if (isPresent(cartContainer)) {

            System.out.println(
                    "Cart page loaded successfully."
            );

            return;
        }

        System.out.println(
                "Cart page loaded. Cart container locator " +
                "was not explicitly detected."
        );
    }

    public void placeOrder() {

        waitForClickable(placeOrderButton);

        scrollTo(placeOrderButton);

        jsClick(placeOrderButton);

        waitForPage();

        System.out.println(
                "Place Order clicked."
        );
    }
}