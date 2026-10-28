package context;

import base.DriverFactory;
import models.FlipkartTestData;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PaymentPage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class TestContext {

    private WebDriver driver;

    private FlipkartTestData testData;

    private HomePage homePage;
    private LoginPage loginPage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;

    public void initialize() {

        DriverFactory.initializeDriver();

        driver = DriverFactory.getDriver();

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        paymentPage = new PaymentPage(driver);
    }

    public void loadTestData(String testCaseId) {

        testData =
                TestDataManager.loadTestData(
                        testCaseId
                );

        System.out.println(
                "Test data loaded for: "
                        + testCaseId
        );
    }

    public WebDriver getDriver() {
        return driver;
    }

    public FlipkartTestData getTestData() {
        return testData;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public SearchResultsPage getSearchResultsPage() {
        return searchResultsPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public CheckoutPage getCheckoutPage() {
        return checkoutPage;
    }

    public PaymentPage getPaymentPage() {
        return paymentPage;
    }

    public void quit() {

        DriverFactory.quitDriver();

        driver = null;
    }
}