package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.config.ConfigReader;

import java.time.Duration;

public final class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {
    }

    public static void initializeDriver() {

        if (driver != null) {
            return;
        }

        String browser = ConfigReader.getBrowser();

        if (!browser.equalsIgnoreCase("chrome")) {
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        }

        ChromeOptions options = new ChromeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
        }

        if (ConfigReader.useChromeProfile()) {
            options.addArguments(
                    "--user-data-dir="
                            + ConfigReader.getChromeProfilePath()
            );
        }

        driver = new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(
                        ConfigReader.getPageLoadTimeout()
                )
        );

        if (ConfigReader.isMaximizeWindow()) {
            driver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized. " +
                    "Call initializeDriver() first."
            );
        }

        return driver;
    }

    public static void quitDriver() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}