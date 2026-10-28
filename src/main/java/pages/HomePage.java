package pages;

import models.FlipkartTestData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.config.ConfigReader;

public class HomePage extends BasePage {

    private final By searchTextBox =
            By.xpath("//input[@name='q']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {

        driver.get(ConfigReader.getBaseUrl());
        waitForPage();

        waitForSearchBox();
    }

    public void searchProduct(FlipkartTestData testData) {

        String product = testData.getSearchProduct();

        if (product == null || product.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Search product is missing from Excel test data."
            );
        }

        System.out.println("Searching for: " + product);

        waitForSearchBox();

        try {

            clear(searchTextBox);
            enterText(searchTextBox, product);

        } catch (org.openqa.selenium.StaleElementReferenceException |
                 org.openqa.selenium.ElementNotInteractableException e) {

            System.out.println(
                    "Search box was refreshed after login. Retrying..."
            );

            hardWait(1000);

            waitForSearchBox();

            clear(searchTextBox);
            enterText(searchTextBox, product);
        }

        browserActions.pressEnter(searchTextBox);

        waitForPage();

        System.out.println("Product search completed.");
    }

    private void waitForSearchBox() {

        waitForElement(searchTextBox);

        try {

            waitForClickable(searchTextBox);

        } catch (Exception e) {

            hardWait(1000);

            waitForElement(searchTextBox);
        }
    }
}