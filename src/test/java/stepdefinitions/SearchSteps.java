package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchSteps {

    private final TestContext testContext;

    public SearchSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I load test data for {string}")
    public void iLoadTestDataFor(String testCaseId) {
        testContext.loadTestData(testCaseId);
    }

    @Given("I am on the Flipkart home page")
    public void iAmOnTheFlipkartHomePage() {
        testContext.getHomePage().open();
    }

    @When("I search for the configured product")
    public void iSearchForTheConfiguredProduct() {
        testContext.getHomePage()
                .searchProduct(testContext.getTestData());
    }

    @When("I apply the configured product filters")
    public void iApplyTheConfiguredProductFilters() {

        testContext.getSearchResultsPage()
                .applyRatingFilter(
                        testContext.getTestData().getCustomerRating()
                );

        testContext.getSearchResultsPage()
                .applyPriceFilter(
                        testContext.getTestData().getMinimumPrice()
                );
    }

    @When("I open the configured product")
    public void iOpenTheConfiguredProduct() {

        testContext.getSearchResultsPage()
                .openProduct(
                        testContext.getTestData().getProductIndex()
                );
    }

    @When("I add the product to cart")
    public void iAddTheProductToCart() {
        testContext.getProductPage().addToCart();
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        testContext.getProductPage().openCart();
    }

    @Then("search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        testContext.getSearchResultsPage()
                .verifyResultsLoaded();
    }

    @Then("the cart should be displayed")
    public void theCartShouldBeDisplayed() {
        testContext.getCartPage()
                .verifyCartLoaded();
    }

    @When("I place the order")
    public void iPlaceTheOrder() {
        testContext.getCartPage()
                .placeOrder();
    }

    @Then("the checkout page should be displayed")
    public void theCheckoutPageShouldBeDisplayed() {
        testContext.getCheckoutPage()
                .verifyCheckoutLoaded();
    }

    @When("I continue from checkout")
    public void iContinueFromCheckout() {
        testContext.getCheckoutPage()
                .clickContinue();
    }

    @Then("the payment page should be displayed")
    public void thePaymentPageShouldBeDisplayed() {
        testContext.getCheckoutPage()
                .verifyPaymentPageLoaded();
    }

    @When("I select the configured payment method")
    public void iSelectTheConfiguredPaymentMethod() {
        testContext.getPaymentPage()
                .selectPaymentMethod(
                        testContext.getTestData()
                );
    }

    @Then("the selected payment method should be displayed")
    public void theSelectedPaymentMethodShouldBeDisplayed() {

        if (!testContext.getPaymentPage()
                .isPaymentPageDisplayed()) {

            throw new AssertionError(
                    "Payment page or payment options were not displayed."
            );
        }

        System.out.println(
                "Selected payment method is displayed successfully."
        );
    }
}