package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.When;

public class LoginSteps {

    private final TestContext testContext;

    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I login with valid credentials")
    public void iLoginWithValidCredentials() {

        testContext.getLoginPage()
                .login(testContext.getTestData());
    }
}