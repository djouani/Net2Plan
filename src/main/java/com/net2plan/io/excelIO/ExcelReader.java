package com.net2plan.io.excelIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 * @author Jorge San Emeterio
 * @date 11-Nov-16
 */
public class ExcelReader
{
    private File excelFile;

    private final String OLE2_EXTENSION = "xls";
    private final String OOXML_EXTENSION = "xlsx";

    public ExcelReader(final File excelFile)
    {
        this.setExcelFile(excelFile);
    }

    public void setExcelFile(final File excelFile)
    {
        this.excelFile = excelFile;
    }

    public void readExcel(final Net2PlanExcelSheetName sheetName)
    {
        // Checking the version of the xml file
        final String fileExtension = FilenameUtils.getExtension(excelFile.getName());

        switch (fileExtension)
        {
            case OLE2_EXTENSION:
                readOLE2(sheetName);
                break;
            case OOXML_EXTENSION:
                readOOXML(sheetName);
                break;
            default:
                // Unknown file format, do not proceed.
                throw new Net2PlanExcelException("Unknown file format: ." + fileExtension);
        }
    }

    private void readOOXML(final Net2PlanExcelSheetName sheetName)
    {
        try
        {
            final XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));
            final XSSFSheet spreadSheet = workbook.getSheet(sheetName.toString());

            if (spreadSheet == null)
            {
                throw new Net2PlanExcelException("Could not find sheet: " + sheetName.toString());
            }

            final Iterator<Row> iterator = spreadSheet.iterator();

            XSSFRow row;
            while (iterator.hasNext())
            {
                row = (XSSFRow) iterator.next();

                final Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext())
                {
                    final Cell cell = cellIterator.next();

                    // TODO: Parse
                }
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void readOLE2(final Net2PlanExcelSheetName sheetName)
    {
        try
        {
            final POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelFile));
            final HSSFWorkbook wb = new HSSFWorkbook(fs);
            final HSSFSheet spreadSheet = wb.getSheet(sheetName.toString());

            if (spreadSheet == null)
            {
                throw new Net2PlanExcelException("Could not find sheet: " + sheetName.toString());
            }

            HSSFRow row;
            HSSFCell cell;

            final int rows = spreadSheet.getLastRowNum() + 1;

            for (int r = 0; r < rows; r++)
            {
                row = spreadSheet.getRow(r);
                if (row != null)
                {
                    for (int c = 0; c < row.getLastCellNum(); c++)
                    {
                        cell = row.getCell(c);
                        if (cell != null)
                        {
                            // TODO: Parse
                        }
                    }
                }
            }
        } catch (Exception ioe)
        {
            ioe.printStackTrace();
        }
    }

    public enum Net2PlanExcelSheetName
    {
        Nodes("Nodes"),
        Links("Links");

        private final String text;

        Net2PlanExcelSheetName(final String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }
}
