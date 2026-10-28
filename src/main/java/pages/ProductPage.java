package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {

    private final By addToCartTextButton =
            By.xpath("(//*[normalize-space()='Add to cart'])[8]");

    private final By addToCartIconButton =
            By.xpath("//*[local-name()='g' and contains(@clip-path,'AddToCart')]/ancestor::div[1]");

    private final By cartButton =
            By.xpath(
                    "//span[normalize-space()='Cart']"
                            + " | //a[contains(@href,'viewcart')]"
                            + " | //*[normalize-space()='Cart']/ancestor::a[1]"
            );

    private final String cartUrl =
            "https://www.flipkart.com/viewcart";

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart() {

        System.out.println();
        System.out.println("========== PRODUCT ==========");

        waitForPage();
        hardWait(2000);

        System.out.println("Checking Add To Cart availability...");

        // Case 1: Normal Add To Cart button is available.
        if (isPresent(addToCartTextButton)) {

            System.out.println("Normal Add To Cart button found.");

            WebElement addButton =
                    driver.findElement(addToCartTextButton);

            scrollTo(addButton);
            browserActions.javaScriptClick(addButton);

            System.out.println("Add To Cart Clicked.");

            hardWait(3000);
            return;
        }

        // Case 2: Flipkart is using the small cart icon.
        if (isPresent(addToCartIconButton)) {

            System.out.println("Normal Add To Cart button not found.");
            System.out.println("Small cart icon Add To Cart button found.");

            WebElement addButton =
                    driver.findElement(addToCartIconButton);

            scrollTo(addButton);
            browserActions.javaScriptClick(addButton);

            System.out.println("Add To Cart Clicked.");

            hardWait(3000);
            return;
        }

        // Case 3: Product may already be present in the cart.
        System.out.println("Add To Cart button was not found.");
        System.out.println("Product may already be present in the cart.");
        System.out.println("Continuing to Cart...");
    }

    public void openCart() {

        System.out.println();
        System.out.println("========== CART ==========");
        System.out.println("Opening Cart...");

        boolean cartOpened = false;

        try {

            if (isPresent(cartButton)) {

                WebElement cart =
                        driver.findElement(cartButton);

                System.out.println("Cart button found.");

                scrollTo(cart);

                try {

                    waitForClickable(cartButton);
                    browserActions.javaScriptClick(cart);

                    hardWait(3000);

                    if (isCartPageLoaded()) {

                        cartOpened = true;

                        System.out.println(
                                "Cart opened successfully using header button."
                        );
                    }

                } catch (Exception e) {

                    System.out.println(
                            "Cart button click was not successful: "
                                    + e.getMessage()
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to interact with header Cart button: "
                            + e.getMessage()
            );
        }

        // Fallback: Open the cart directly if the header button fails.
        if (!cartOpened) {

            System.out.println(
                    "Header Cart click did not navigate to the cart."
            );

            System.out.println(
                    "Opening cart using direct URL..."
            );

            driver.get(cartUrl);

            waitForPage();
            hardWait(2000);

            if (!isCartPageLoaded()) {

                throw new AssertionError(
                        "Unable to open the Flipkart cart page."
                );
            }

            System.out.println(
                    "Cart opened successfully using direct URL."
            );
        }
    }

    private boolean isCartPageLoaded() {

        String currentUrl = driver.getCurrentUrl();

        return currentUrl != null
                && currentUrl.contains("/viewcart");
    }
}