package com.net2plan.misc;

import com.net2plan.io.excelIO.ExcelReader;

import java.io.File;

/**
 * @author Jorge San Emeterio
 * @date 11-Nov-16
 */
public class ExcelMain
{
    public static void main(String[] args)
    {
        File file = new File("C:\\Users\\Jorge\\Desktop\\Ejemplo.xlsx");
        ExcelReader reader = new ExcelReader(file);
        reader.readExcel(null);
    }
}
