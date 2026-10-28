package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static String reportFolderPath;

    private ExtentReportManager() {
        // Utility class
    }

    public static void initializeReport() {

        if (extentReports != null) {
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                .format(new Date());

        reportFolderPath = System.getProperty("user.dir")
                + File.separator
                + "Reports"
                + File.separator
                + timestamp;

        File reportFolder = new File(reportFolderPath);

        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }

        String reportPath = reportFolderPath
                + File.separator
                + "ExtentReport.html";

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportPath);

        sparkReporter.config().setDocumentTitle("Flipkart Automation Report");
        sparkReporter.config().setReportName("Test Execution Report");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo("Project", "Flipkart Automation Framework");
        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("Browser", "Chrome");
        extentReports.setSystemInfo("Automation", "Selenium + Cucumber + Java");
    }

    public static void createTest(String scenarioName) {

        if (extentReports == null) {
            initializeReport();
        }

        extentTest = extentReports.createTest(scenarioName);
    }

    public static ExtentTest getTest() {
        return extentTest;
    }

    public static String getReportFolderPath() {
        return reportFolderPath;
    }

    public static void flushReport() {

        if (extentReports != null) {
            extentReports.flush();
        }
    }
}