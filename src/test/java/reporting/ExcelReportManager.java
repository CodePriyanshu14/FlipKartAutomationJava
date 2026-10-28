package reporting;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelReportManager {

    private static final List<TestResult> testResults = new ArrayList<>();

    private ExcelReportManager() {
        // Utility class - only static methods and fields, without requiring instantiation.
    }

    public static void addResult(
            String scenarioName,
            String status,
            String error,
            long durationInMillis) {

        testResults.add(
                new TestResult(
                        scenarioName,
                        status,
                        error,
                        durationInMillis,
                        LocalDateTime.now()
                )
        );
    }

    public static void generateReport(String reportFolderPath) {

        String filePath = reportFolderPath
                + java.io.File.separator
                + "TestExecutionReport.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Test Execution Report");

            createHeader(sheet);

            int rowNumber = 1;

            for (int i = 0; i < testResults.size(); i++) {

                TestResult result = testResults.get(i);

                Row row = sheet.createRow(rowNumber++);

                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(result.scenarioName);
                row.createCell(2).setCellValue(result.status);
                row.createCell(3).setCellValue(result.error);
                row.createCell(4).setCellValue(
                        formatDuration(result.durationInMillis)
                );
                row.createCell(5).setCellValue(
                        result.executionDateTime.format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd HH:mm:ss"
                                )
                        )
                );
            }

            autoSizeColumns(sheet);

            try (FileOutputStream outputStream =
                         new FileOutputStream(filePath)) {

                workbook.write(outputStream);
            }

            System.out.println(
                    "Excel report generated: " + filePath
            );

        } catch (IOException e) {

            System.out.println(
                    "Excel report generation failed: "
                            + e.getMessage()
            );
        }
    }

    public static void clearResults() {
        testResults.clear();
    }

    private static void createHeader(Sheet sheet) {

        Row headerRow = sheet.createRow(0);

        String[] headers = {
                "Test No.",
                "Scenario Name",
                "Status",
                "Error",
                "Duration",
                "Execution Date/Time"
        };

        CellStyle headerStyle =
                createHeaderStyle(sheet.getWorkbook());

        for (int i = 0; i < headers.length; i++) {

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private static CellStyle createHeaderStyle(
            Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);

        style.setFont(font);

        return style;
    }

    private static void autoSizeColumns(Sheet sheet) {

        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static String formatDuration(long durationInMillis) {

        Duration duration =
                Duration.ofMillis(durationInMillis);

        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();

        if (minutes > 0) {
            return minutes + " min " + seconds + " sec";
        }

        return seconds + " sec";
    }

    private static class TestResult {

        private final String scenarioName;
        private final String status;
        private final String error;
        private final long durationInMillis;
        private final LocalDateTime executionDateTime;

        private TestResult(
                String scenarioName,
                String status,
                String error,
                long durationInMillis,
                LocalDateTime executionDateTime) {

            this.scenarioName = scenarioName;
            this.status = status;
            this.error = error;
            this.durationInMillis = durationInMillis;
            this.executionDateTime = executionDateTime;
        }
    }
}