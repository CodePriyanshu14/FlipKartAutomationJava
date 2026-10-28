package context;

import models.FlipkartTestData;
import utils.excel.ExcelReader;

public final class TestDataManager {

    private TestDataManager() {
    }

    public static FlipkartTestData loadTestData(
            String testCaseId) {

        ExcelReader excelReader =
                new ExcelReader();

        return excelReader.getData(
                testCaseId,
                FlipkartTestData.class
        );
    }
}