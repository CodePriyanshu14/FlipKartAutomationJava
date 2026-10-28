package utils.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.config.ConfigReader;
import utils.helpers.WaitHelper;

import java.util.ArrayList;
import java.util.List;

public class BrowserActions {

    private final WebDriver driver;
    private final WaitHelper waitHelper;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
        this.waitHelper =
                new WaitHelper(driver, ConfigReader.getExplicitWait());
    }

    public WebElement getElement(By locator) {
        return waitHelper.waitUntilVisible(locator);
    }

    public List<WebElement> getElements(By locator) {
        return waitHelper.waitUntilAllVisible(locator);
    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return waitHelper.waitUntilVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementEnabled(By locator) {
        try {
            return waitHelper.waitUntilVisible(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void click(By locator) {
        scrollIntoView(locator);
        waitHelper.waitUntilClickable(locator).click();
    }

    public void click(WebElement element) {
        scrollIntoView(element);
        element.click();
    }

    public void doubleClick(By locator) {
        WebElement element =
                waitHelper.waitUntilClickable(locator);

        new Actions(driver)
                .doubleClick(element)
                .perform();
    }

    public void rightClick(By locator) {
        WebElement element =
                waitHelper.waitUntilClickable(locator);

        new Actions(driver)
                .contextClick(element)
                .perform();
    }

    public void javaScriptClick(By locator) {
        WebElement element =
                waitHelper.waitUntilClickable(locator);

        javaScriptClick(element);
    }

    public void javaScriptClick(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element
                );
    }

    public void enterText(By locator, String text) {
        WebElement element =
                waitHelper.waitUntilVisible(locator);

        element.clear();
        element.sendKeys(text);
    }

    public void enterText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    public void clear(By locator) {
        waitHelper.waitUntilVisible(locator).clear();
    }

    public void sendKeys(By locator, String text) {
        waitHelper.waitUntilVisible(locator)
                .sendKeys(text);
    }

    public void pressEnter(By locator) {
        waitHelper.waitUntilVisible(locator)
                .sendKeys(Keys.ENTER);
    }

    public void pressEscape() {
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();
    }

    public String getText(By locator) {
        return waitHelper.waitUntilVisible(locator).getText();
    }

    public String getAttribute(
            By locator,
            String attribute) {

        return waitHelper.waitUntilVisible(locator)
                .getAttribute(attribute);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void scrollIntoView(By locator) {
        WebElement element =
                waitHelper.waitUntilVisible(locator);

        scrollIntoView(element);
    }

    public void scrollIntoView(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({behavior:'instant',block:'center'});",
                        element
                );
    }

    public void scrollToTop() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0,0);");
    }

    public void scrollToBottom() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "window.scrollTo(0,document.body.scrollHeight);"
                );
    }

    public void hover(By locator) {
        WebElement element =
                waitHelper.waitUntilVisible(locator);

        new Actions(driver)
                .moveToElement(element)
                .perform();
    }

    public void dragAndDrop(
            By source,
            By target) {

        WebElement sourceElement =
                waitHelper.waitUntilVisible(source);

        WebElement targetElement =
                waitHelper.waitUntilVisible(target);

        new Actions(driver)
                .dragAndDrop(
                        sourceElement,
                        targetElement
                )
                .perform();
    }

    public void selectByText(
            By locator,
            String text) {

        Select select =
                new Select(
                        waitHelper.waitUntilVisible(locator)
                );

        select.selectByVisibleText(text);
    }

    public void selectByValue(
            By locator,
            String value) {

        Select select =
                new Select(
                        waitHelper.waitUntilVisible(locator)
                );

        select.selectByValue(value);
    }

    public void selectByIndex(
            By locator,
            int index) {

        Select select =
                new Select(
                        waitHelper.waitUntilVisible(locator)
                );

        select.selectByIndex(index);
    }

    public void switchToLastWindow() {

        List<String> windows =
                new ArrayList<>(
                        driver.getWindowHandles()
                );

        driver.switchTo().window(
                windows.get(windows.size() - 1)
        );
    }

    public void switchToWindow(int index) {

        List<String> windows =
                new ArrayList<>(
                        driver.getWindowHandles()
                );

        driver.switchTo().window(
                windows.get(index)
        );
    }

    public void switchToParentWindow() {

        List<String> windows =
                new ArrayList<>(
                        driver.getWindowHandles()
                );

        driver.switchTo().window(
                windows.get(0)
        );
    }

    public void closeCurrentWindow() {

        driver.close();

        if (!driver.getWindowHandles().isEmpty()) {
            switchToLastWindow();
        }
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
        waitHelper.waitForPageLoad();
    }

    public void refresh() {
        driver.navigate().refresh();
        waitHelper.waitForPageLoad();
    }

    public void back() {
        driver.navigate().back();
        waitHelper.waitForPageLoad();
    }

    public void forward() {
        driver.navigate().forward();
        waitHelper.waitForPageLoad();
    }

    public void executeJavaScript(String script) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(script);
    }

    public Object executeJavaScript(
            String script,
            Object... args) {

        return ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(script, args);
    }
}