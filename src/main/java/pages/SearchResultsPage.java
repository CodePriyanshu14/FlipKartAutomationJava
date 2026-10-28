package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends BasePage {

    private final By productCards =
            By.xpath("//div[@data-id]");

    private final By ratingFilter =
            By.xpath(
                    "//div[@title='4★ & above']//label"
            );

    private final By minimumPriceDropdown =
            By.xpath(
                    "//select[option[@value='1000']]"
            );

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void applyRatingFilter(String rating) {

        if (rating == null || rating.trim().isEmpty()) {
            return;
        }

        System.out.println(
                "Applying rating filter: " + rating
        );

        waitForElement(productCards);

        if (isPresent(ratingFilter)) {

            scrollTo(ratingFilter);

            jsClick(ratingFilter);

            waitForProductsRefresh();

            System.out.println(
                    "Rating filter applied."
            );

        } else {

            System.out.println(
                    "Rating filter not available."
            );
        }
    }

    public void applyPriceFilter(double minimumPrice) {

        if (minimumPrice <= 0) {
            return;
        }

        System.out.println(
                "Applying minimum price filter: ₹"
                        + minimumPrice
        );

        waitForElement(productCards);

        if (!isPresent(minimumPriceDropdown)) {

            System.out.println(
                    "Minimum price dropdown not available."
            );

            return;
        }

        scrollTo(minimumPriceDropdown);

        String value =
                String.valueOf(
                        (int) minimumPrice
                );

        try {

            selectByValue(
                    minimumPriceDropdown,
                    value
            );

            waitForProductsRefresh();

            System.out.println(
                    "Minimum price filter applied."
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to apply price filter: "
                            + e.getMessage()
            );
        }
    }

    public void openProduct(int productIndex) {

        if (productIndex <= 0) {

            throw new IllegalArgumentException(
                    "Product index must be greater than zero."
            );
        }

        waitForElement(productCards);

        List<WebElement> products =
                driver.findElements(productCards);

        System.out.println(
                "Products available: "
                        + products.size()
        );

        if (products.isEmpty()) {

            throw new AssertionError(
                    "No products available."
            );
        }

        if (productIndex > products.size()) {

            throw new AssertionError(
                    "Requested product index "
                            + productIndex
                            + " but only "
                            + products.size()
                            + " products were found."
            );
        }

        WebElement product =
                products.get(productIndex - 1);

        scrollTo(product);

        WebElement link =
                product.findElement(
                        By.tagName("a")
                );

        String productUrl =
                link.getAttribute("href");

        if (productUrl == null ||
                productUrl.isBlank()) {

            throw new AssertionError(
                    "Product URL was not found."
            );
        }

        System.out.println(
                "Opening product: "
                        + productUrl
        );

        driver.get(productUrl);

        waitForPage();

        System.out.println(
                "Product page loaded."
        );
    }

    public void verifyResultsLoaded() {

        waitForElement(productCards);

        List<WebElement> products =
                driver.findElements(productCards);

        if (products.isEmpty()) {

            throw new AssertionError(
                    "No products found after search/filter."
            );
        }

        System.out.println(
                "Products available: "
                        + products.size()
        );
    }

    private void waitForProductsRefresh() {

        hardWait(2500);

        waitForElement(productCards);

        hardWait(1000);
    }
}