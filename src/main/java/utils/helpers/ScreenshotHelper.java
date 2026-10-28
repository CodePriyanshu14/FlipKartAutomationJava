package utils.helpers;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {

    private final WebDriver driver;

    public ScreenshotHelper(WebDriver driver) {
        this.driver = driver;
    }

    public String captureScreenshot(
            String scenarioName) {

        Path screenshotDirectory =
                Paths.get("Screenshots");

        try {

            Files.createDirectories(
                    screenshotDirectory
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to create screenshot directory.",
                    e
            );
        }

        String timestamp =
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyyMMdd_HHmmss"
                                )
                        );

        String safeScenarioName =
                scenarioName
                        .replaceAll(
                                "[^a-zA-Z0-9._-]",
                                "_"
                        );

        Path destination =
                screenshotDirectory.resolve(
                        safeScenarioName
                                + "_"
                                + timestamp
                                + ".png"
                );

        File screenshot =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(
                                OutputType.FILE
                        );

        try {

            Files.copy(
                    screenshot.toPath(),
                    destination
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to save screenshot.",
                    e
            );
        }

        return destination
                .toAbsolutePath()
                .toString();
    }
}