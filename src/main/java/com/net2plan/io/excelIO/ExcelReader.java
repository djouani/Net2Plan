package com.net2plan.io.excelIO;

import com.google.common.base.Splitter;
import com.net2plan.gui.utils.INetworkCallback;
import com.net2plan.interfaces.networkDesign.Link;
import com.net2plan.interfaces.networkDesign.Net2PlanException;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.interfaces.networkDesign.Node;
import com.net2plan.internal.ErrorHandling;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author Jorge San Emeterio
 * @date 11-Nov-16
 */
public class ExcelReader
{
    private File excelFile;
    private Net2PlanExcelSheetName excelSheetName;
    private INetworkCallback callback;
    private NetPlan netPlan;

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
     * Reads a given sheet of the excel file and transforms its contents into the given NetPlan.
     */
    public NetPlan readExcel(final INetworkCallback callback)
    {
        this.callback = callback;
        this.netPlan = new NetPlan();

        // Checking the version of the xml file
        final String fileExtension = FilenameUtils.getExtension(excelFile.getName());

        try
        {
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
        } catch (Net2PlanException ex)
        {
            ex.printStackTrace();
            ErrorHandling.showErrorDialog("Error while reading Excel");

            this.netPlan = new NetPlan();
        }

        return netPlan;
    }

    /**
     * Excel reader for Excel formats 2007+
     */
    private void readOOXML()
    {
        try
        {
            final XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));

            final int numberOfSheets = workbook.getNumberOfSheets();
            // Going through all sheets.
            for (int i = 0; i < numberOfSheets; i++)
            {
                final XSSFSheet spreadSheet = workbook.getSheetAt(i);

                if (i == 0 && !spreadSheet.getSheetName().equals(ExcelConstants.SHEET_NODES))
                {
                    throw new Net2PlanExcelException("Nodes sheet must be first page on excel file.");
                }

                // Finding out what sheet I an on.
                final String sheetName = spreadSheet.getSheetName();
                excelSheetName = Net2PlanExcelSheetName.valueOf(sheetName);

                final DataFormatter formatter = new DataFormatter();
                final FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                // The first row must always be the header row
                // Contains the header columns in order
                final LinkedList<String> tableHeader = new LinkedList<>();

                // The first iteration is not parsed. Instead, its values are later used as the keys for the rows' maps.
                XSSFRow row;
                final Iterator<Row> iterator = spreadSheet.iterator();
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
            }
        } catch (IOException e)
        {
            e.printStackTrace();
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
            final HSSFSheet spreadSheet = wb.getSheet(excelSheetName.toString());

            if (spreadSheet == null)
            {
                throw new Net2PlanExcelException("Could not find sheet: " + excelSheetName.toString());
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
    private void doRead(final Map<String, String> headerToValue)
    {
        switch (excelSheetName)
        {
            case Nodes:
                // Creating the nodes of the topology
                final Node node = netPlan.addNode(Double.parseDouble(headerToValue.get(ExcelConstants.NODE_X)), Double.parseDouble(headerToValue.get(ExcelConstants.NODE_Y)), headerToValue.get(ExcelConstants.NODE_NAME), readAttributes(headerToValue.get(ExcelConstants.NODE_ATTRIBUTES)));
                callback.getTopologyPanel().getCanvas().addNode(node);
                break;
            case Links:
                final Link link = netPlan.addLink(netPlan.getNodeByName(headerToValue.get(ExcelConstants.LINK_ORIGIN)), netPlan.getNodeByName(headerToValue.get(ExcelConstants.LINK_DESTINATION)), Double.parseDouble(headerToValue.get(ExcelConstants.LINK_CAPACITY)), Double.parseDouble(headerToValue.get(ExcelConstants.LINK_LENGTH)), Double.parseDouble(headerToValue.get(ExcelConstants.LINK_PROPAGATION)), readAttributes(headerToValue.get(ExcelConstants.LINK_ATTRIBUTES)));
                callback.getTopologyPanel().getCanvas().addLink(link);
                break;
        }
    }

    private Map<String, String> readAttributes(final String attributes)
    {
        // Structure => key:value;key:value...
        if (attributes == null || attributes.isEmpty()) return null;
        else
            return Splitter.on(ExcelConstants.ATTRIBUTE_SEPARATOR).withKeyValueSeparator(ExcelConstants.ATTRIBUTE_PAIR_SEPARATOR).split(attributes);
    }

    public enum Net2PlanExcelSheetName
    {
        Nodes(ExcelConstants.SHEET_NODES),
        Links(ExcelConstants.SHEET_LINKS);

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
