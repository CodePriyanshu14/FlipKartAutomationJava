package hooks;

import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import reporting.ExcelReportManager;
import reporting.ExtentReportManager;

public class Hooks {

    private final TestContext testContext;

    private long scenarioStartTime;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {

        System.out.println(
                "\n========== STARTING SCENARIO =========="
        );

        scenarioStartTime = System.currentTimeMillis();

        // Initialize Extent Reporting
        ExtentReportManager.initializeReport();

        // Create Extent test for current scenario
        ExtentReportManager.createTest(
                scenario.getName()
        );

        // Initialize framework
        testContext.initialize();
    }

    @After
    public void tearDown(Scenario scenario) {

        long scenarioDuration =
                System.currentTimeMillis() - scenarioStartTime;

        System.out.println(
                "Scenario: " + scenario.getName()
        );

        String status;
        String error = "";

        if (scenario.isFailed()) {

            status = "FAIL";
            error = "Scenario failed";

            if (ExtentReportManager.getTest() != null) {

                ExtentReportManager.getTest()
                        .fail("Scenario Failed");
            }

        } else {

            status = "PASS";

            if (ExtentReportManager.getTest() != null) {

                ExtentReportManager.getTest()
                        .pass("Scenario Passed");
            }
        }

        // Add result to Excel report
        ExcelReportManager.addResult(
                scenario.getName(),
                status,
                error,
                scenarioDuration
        );

        // Existing Cucumber failure screenshot
        if (scenario.isFailed()
                && testContext.getDriver() != null) {

            try {

                byte[] screenshot =
                        ((TakesScreenshot)
                                testContext.getDriver())
                                .getScreenshotAs(
                                        OutputType.BYTES
                                );

                scenario.attach(
                        screenshot,
                        "image/png",
                        "Failure Screenshot"
                );

            } catch (Exception e) {

                System.out.println(
                        "Screenshot failed: "
                                + e.getMessage()
                );
            }
        }

        // Flush Extent report
        ExtentReportManager.flushReport();

        // Quit browser
        testContext.quit();

        System.out.println(
                "========== SCENARIO FINISHED ==========\n"
        );
    }

    @AfterAll
    public static void generateExcelReport() {

        String reportFolderPath =
                ExtentReportManager.getReportFolderPath();

        if (reportFolderPath != null) {

            ExcelReportManager.generateReport(
                    reportFolderPath
            );
        }
    }
}