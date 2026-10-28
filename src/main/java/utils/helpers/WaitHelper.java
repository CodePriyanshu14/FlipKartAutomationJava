package utils.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitHelper {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitHelper(
            WebDriver driver,
            int timeoutInSeconds) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(timeoutInSeconds)
        );

        this.wait.ignoring(
                NoSuchElementException.class,
                StaleElementReferenceException.class
        );
    }

    public WebElement waitUntilVisible(By locator) {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public WebElement waitUntilClickable(By locator) {

        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    public WebElement waitUntilPresent(By locator) {

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );
    }

    public List<WebElement> waitUntilAllVisible(By locator) {

        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)
        );
    }

    public boolean waitUntilInvisible(By locator) {

        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }

    public boolean waitUntilTextPresent(
            By locator,
            String expectedText) {

        return wait.until(driver -> {

            try {

                String actualText =
                        driver.findElement(locator).getText();

                return actualText
                        .toLowerCase()
                        .contains(
                                expectedText.toLowerCase()
                        );

            } catch (Exception e) {
                return false;
            }
        });
    }

    public boolean waitUntilAttributeContains(
            By locator,
            String attribute,
            String value) {

        return wait.until(driver -> {

            try {

                String attributeValue =
                        driver.findElement(locator)
                                .getAttribute(attribute);

                return attributeValue != null &&
                        attributeValue
                                .toLowerCase()
                                .contains(
                                        value.toLowerCase()
                                );

            } catch (Exception e) {
                return false;
            }
        });
    }

    public boolean waitUntilTitleContains(String title) {

        return wait.until(driver ->
                driver.getTitle()
                        .toLowerCase()
                        .contains(
                                title.toLowerCase()
                        )
        );
    }

    public boolean waitUntilUrlContains(String urlPart) {

        return wait.until(driver ->
                driver.getCurrentUrl()
                        .toLowerCase()
                        .contains(
                                urlPart.toLowerCase()
                        )
        );
    }

    public boolean waitUntilNumberOfWindows(
            int expectedCount) {

        return wait.until(driver ->
                driver.getWindowHandles().size()
                        == expectedCount
        );
    }

    public boolean waitUntilElementCount(
            By locator,
            int expectedCount) {

        return wait.until(driver ->
                driver.findElements(locator).size()
                        >= expectedCount
        );
    }

    public void waitForPageLoad() {

        wait.until(driver ->
                driver.getPageSource() != null
        );
    }

    public void waitForPageReady() {
        waitForPageLoad();
    }

    public void hardWait(int milliseconds) {

        try {

            Thread.sleep(milliseconds);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "Thread interrupted during wait.",
                    e
            );
        }
    }
}