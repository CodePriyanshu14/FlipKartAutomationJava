package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.actions.BrowserActions;
import utils.helpers.ScreenshotHelper;
import utils.helpers.WaitHelper;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final BrowserActions browserActions;
    protected final WaitHelper wait;
    protected final ScreenshotHelper screenshot;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.browserActions = new BrowserActions(driver);
        this.wait = new WaitHelper(driver, 20);
        this.screenshot = new ScreenshotHelper(driver);
    }

    protected void click(By locator) {
        browserActions.click(locator);
    }

    protected void jsClick(By locator) {
        browserActions.javaScriptClick(locator);
    }

    protected void enterText(By locator, String text) {
        browserActions.enterText(locator, text);
    }

    protected void clear(By locator) {
        browserActions.clear(locator);
    }

    protected String getText(By locator) {
        return browserActions.getText(locator);
    }

    protected boolean isPresent(By locator) {
        return browserActions.isElementPresent(locator);
    }

    protected boolean isDisplayed(By locator) {
        return browserActions.isElementDisplayed(locator);
    }

    protected void waitForElement(By locator) {
        wait.waitUntilVisible(locator);
    }

    protected void waitForClickable(By locator) {
        wait.waitUntilClickable(locator);
    }

    protected void waitForPage() {
        wait.waitForPageReady();
    }

    protected void hardWait(int milliseconds) {
        wait.hardWait(milliseconds);
    }

    protected void selectByValue(By locator, String value) {
        browserActions.selectByValue(locator, value);
    }

    protected void selectByText(By locator, String text) {
        browserActions.selectByText(locator, text);
    }

    protected void scrollTo(By locator) {
        browserActions.scrollIntoView(locator);
    }

    protected void scrollTo(WebElement element) {
        browserActions.scrollIntoView(element);
    }

    protected void captureScreenshot(String fileName) {
        screenshot.captureScreenshot(fileName);
    }
}