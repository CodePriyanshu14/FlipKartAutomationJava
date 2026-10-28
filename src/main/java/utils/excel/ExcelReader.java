package utils.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import utils.config.ConfigReader;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    private final Path filePath;

    public ExcelReader() {

        filePath = Paths.get(
                ConfigReader.getTestDataPath()
        );

        System.out.println(
                "Excel Path : " + filePath.toAbsolutePath()
        );
    }

    public <T> T getData(
            String testCaseId,
            Class<T> clazz) {

        File file = filePath.toFile();

        if (!file.exists()) {
            throw new RuntimeException(
                    "Excel file not found: "
                            + file.getAbsolutePath()
            );
        }

        try (
                FileInputStream inputStream =
                        new FileInputStream(file);

                Workbook workbook =
                        WorkbookFactory.create(inputStream)
        ) {

            Sheet sheet = workbook.getSheetAt(0);

            Map<String, Integer> headers =
                    readHeaders(sheet);

            Integer testCaseColumn =
                    headers.get("testcaseid");

            if (testCaseColumn == null) {
                throw new RuntimeException(
                        "TestCaseId column not found."
                );
            }

            for (Row row : sheet) {

                if (row.getRowNum() == 0) {
                    continue;
                }

                String currentTestCase =
                        getCellValue(
                                row.getCell(testCaseColumn)
                        );

                if (!currentTestCase.equalsIgnoreCase(testCaseId)) {
                    continue;
                }

                T data = clazz
                        .getDeclaredConstructor()
                        .newInstance();

                populateObject(
                        data,
                        row,
                        headers
                );

                return data;
            }

            throw new RuntimeException(
                    "Test Case '"
                            + testCaseId
                            + "' not found."
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to read Excel test data.",
                    e
            );
        }
    }

    private Map<String, Integer> readHeaders(
            Sheet sheet) {

        Map<String, Integer> headers =
                new HashMap<>();

        Row headerRow = sheet.getRow(0);

        for (Cell cell : headerRow) {

            String name =
                    getCellValue(cell)
                            .trim()
                            .toLowerCase();

            headers.put(
                    name,
                    cell.getColumnIndex()
            );
        }

        return headers;
    }

    private <T> void populateObject(
            T object,
            Row row,
            Map<String, Integer> headers)
            throws IllegalAccessException {

        for (Field field :
                object.getClass().getDeclaredFields()) {

            String propertyName =
                    field.getName().toLowerCase();

            Integer column =
                    headers.get(propertyName);

            if (column == null) {
                continue;
            }

            String value =
                    getCellValue(
                            row.getCell(column)
                    );

            field.setAccessible(true);

            setFieldValue(
                    object,
                    field,
                    value
            );
        }
    }

    private void setFieldValue(
            Object object,
            Field field,
            String value)
            throws IllegalAccessException {

        Class<?> type = field.getType();

        if (type == String.class) {

            field.set(object, value);

        } else if (type == int.class
                || type == Integer.class) {

            field.set(
                    object,
                    Integer.parseInt(value)
            );

        } else if (type == double.class
                || type == Double.class) {

            field.set(
                    object,
                    Double.parseDouble(value)
            );

        } else if (type == boolean.class
                || type == Boolean.class) {

            field.set(
                    object,
                    Boolean.parseBoolean(value)
            );
        }
    }

    private String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter =
                new DataFormatter();

        return formatter.formatCellValue(cell);
    }
}