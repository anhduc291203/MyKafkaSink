package wi.kafka.sink.tool;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import wi.kafka.sink.MappingProperties;

public class ReadExcelFile
{
    private static final String DEFAULT_CONFIG_FILE = "src/main/resources/test.xlsx";



    public static String[][] getDataFromExcel()
    {
        try
        {
            File file = new File(DEFAULT_CONFIG_FILE);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            int rowCount = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(0);
            int columnCount = 0;
            if (firstRow != null) {
                columnCount = firstRow.getPhysicalNumberOfCells();
            }
            String[][] data = new String[rowCount][columnCount];

            int rowIndex = 0;

            for (Row row : sheet) {
                int columnIndex = 0;

                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            data[rowIndex][columnIndex] = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data[rowIndex][columnIndex] = String.valueOf(cell.getNumericCellValue());
                            break;
                        default:
                    }

                    columnIndex++;
                }

                rowIndex++;
            }
            return data;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new String[0][];
    }

    public static void main(String args[]){

        System.out.println(Arrays.deepToString(getDataFromExcel()));
    }
}


