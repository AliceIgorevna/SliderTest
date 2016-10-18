package engine.data;

import engine.report.ReportHolder;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Алиса on 15.10.2016.
 */
public class TestDataHolder {
    public static Object[][] parse(String excelFullName, String sheetName) {
        Object[][] testData = null;
        Row row = null;
        try (FileInputStream file = new FileInputStream(new File(excelFullName));
             HSSFWorkbook workbook = new HSSFWorkbook(file)) {
            HSSFSheet sheet = workbook.getSheet(sheetName);
            int numberOfRows = sheet.getPhysicalNumberOfRows() - 1;
            int numberOfCells = sheet.getRow(0).getPhysicalNumberOfCells();
            testData = new Object[numberOfRows][numberOfCells];
            for (int i = 1; i <= numberOfRows; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < numberOfCells; j++) {
                    testData[i - 1][j] = row.getCell(j).toString();
                }
            }
        } catch (Exception ex) {
            ReportHolder.stepReporter(1, ex.getMessage(), "Excel data should be read properly");
        }
        return testData;
    }
}
