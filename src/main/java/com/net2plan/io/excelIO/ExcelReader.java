package com.net2plan.io.excelIO;

import com.net2plan.interfaces.networkDesign.NetPlan;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author Jorge San Emeterio
 * @date 11-Nov-16
 */
public class ExcelReader
{
    private File excelFile;
    private Net2PlanExcelSheetName sheetName;

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

    /**
     * Method through which the parser is launched.
     * Reads a given sheet of the excel file and transforms its contents into a NetPlan.
     *
     * @param sheetName Name of the sheet that is going to be read.
     */
    public void readExcel(final Net2PlanExcelSheetName sheetName)
    {
        // Checking the version of the xml file
        final String fileExtension = FilenameUtils.getExtension(excelFile.getName());

        this.sheetName = sheetName;

        switch (fileExtension)
        {
            case OLE2_EXTENSION:
                readOLE2();
                break;
            case OOXML_EXTENSION:
                readOOXML();
                break;
            default:
                // Unknown file format, do not proceed.
                throw new Net2PlanExcelException("Unknown file format: ." + fileExtension);
        }
    }

    /**
     * Excel reader for Excel formats 2007+
     */
    private void readOOXML()
    {
        try
        {
            final XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));
            final XSSFSheet spreadSheet = workbook.getSheet(sheetName.toString());

            final DataFormatter formatter = new DataFormatter();
            final FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            if (spreadSheet == null)
            {
                throw new Net2PlanExcelException("Could not find sheet: " + sheetName.toString());
            }

            final Iterator<Row> iterator = spreadSheet.iterator();

            // The first row must always be the header row
            // Contains the header columns in order
            final LinkedList<String> tableHeader = new LinkedList<>();

            XSSFRow row;

            // The first iteration is not parsed. Instead, its values are later used as the keys for the rows' maps.
            if (iterator.hasNext())
            {
                row = (XSSFRow) iterator.next();

                final Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    final Cell cell = cellIterator.next();

                    // All parameters will be read as String.
                    evaluator.evaluate(cell);
                    final String value = formatter.formatCellValue(cell, evaluator);

                    tableHeader.add(value);
                }

                while (iterator.hasNext())
                {
                    row = (XSSFRow) iterator.next();

                    final Map<String, String> headerToValueMap = new HashMap<>();

                    final Iterator<Cell> cIterator = row.cellIterator();
                    while (cIterator.hasNext())
                    {
                        final Cell cell = cIterator.next();

                        // All parameters will be read as String.
                        evaluator.evaluate(cell);
                        final String value = formatter.formatCellValue(cell, evaluator);

                        headerToValueMap.put(tableHeader.get(cell.getColumnIndex()), value);
                    }

                    // Parse row
                    doRead(headerToValueMap);
                }
            }


        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Excel reader for Excel formats 2007-.
     */
    private void readOLE2()
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

    /**
     * Actual parser
     */
    private void doRead(final Map<String, String> headerToValueMap)
    {
        switch (sheetName)
        {
            case Nodes:
                // Creating the nodes of the topology

                break;
            case Links:
                break;
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
