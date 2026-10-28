package utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties =
            new Properties();

    static {

        try (
                InputStream input =
                        ConfigReader.class
                                .getClassLoader()
                                .getResourceAsStream(
                                        "config.properties"
                                )
        ) {

            if (input == null) {

                throw new RuntimeException(
                        "config.properties not found in test resources."
                );
            }

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load config.properties.",
                    e
            );
        }
    }

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return getRequiredProperty("baseUrl");
    }

    public static String getBrowser() {
        return getRequiredProperty("browser");
    }

    public static int getImplicitWait() {
        return getIntProperty("implicitWait");
    }

    public static int getExplicitWait() {
        return getIntProperty("explicitWait");
    }

    public static int getPageLoadTimeout() {
        return getIntProperty("pageLoadTimeout");
    }

    public static boolean isMaximizeWindow() {
        return getBooleanProperty("maximizeWindow");
    }

    public static boolean isHeadless() {
        return getBooleanProperty("headless");
    }

    public static boolean useChromeProfile() {
        return getBooleanProperty("useChromeProfile");
    }

    public static String getChromeProfilePath() {
        return getRequiredProperty("chromeProfilePath");
    }

    public static String getTestDataPath() {
        return getRequiredProperty("testDataPath");
    }

    public static String getScreenshotPath() {
        return getRequiredProperty("screenshotPath");
    }

    public static String getReportPath() {
        return getRequiredProperty("reportPath");
    }

    private static String getRequiredProperty(
            String key) {

        String value =
                properties.getProperty(key);

        if (value == null ||
                value.trim().isEmpty()) {

            throw new RuntimeException(
                    "Missing configuration property: "
                            + key
            );
        }

        return value.trim();
    }

    private static int getIntProperty(
            String key) {

        try {

            return Integer.parseInt(
                    getRequiredProperty(key)
            );

        } catch (NumberFormatException e) {

            throw new RuntimeException(
                    "Invalid integer value for property: "
                            + key,
                    e
            );
        }
    }

    private static boolean getBooleanProperty(
            String key) {

        return Boolean.parseBoolean(
                getRequiredProperty(key)
        );
    }
}