package com.parser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CreatorXLS<T> {
    private Class<T> clazz;

    public CreatorXLS(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void createFile(List<T> series, String path, String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        //Otwieramy nowy arkusz
        HSSFWorkbook workbook = new HSSFWorkbook(); //reprezentant pliku exel
        HSSFSheet sheet = workbook.createSheet("sheet"); //reprezentant arkuszy
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.AQUA.getIndex());

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);


        List<String> columns = new ArrayList<>(); //ładujemy/tworzymy  kolumny
        for (Field f : clazz.getDeclaredFields()) {
            columns.add(f.getName());
        }

        Row headerRow = sheet.createRow(0); //tworzenie naglowku wiersza
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = headerRow.createCell(i); //tworzenie tyle komurek ile mamy w liscie danych
            cell.setCellValue(columns.get(i)); //ustawiamy wartości komurek
            //todo dodac style
            cell.setCellStyle(cellStyle);
        }
        for (int i = 0; i < series.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);

            for (int j = 0; j < columns.size(); j++) {
                HSSFCell cell = row.createCell(j);
                Method method = series.get(i)
                        .getClass()
                        .getMethod("get" + columns.get(j).substring(0, 1).toUpperCase() + columns.get(j).substring(1));

                Object result = method.invoke(series.get(i));
                cell.setCellValue(String.valueOf(result));

            }
        }
        for (int i = 0; i < columns.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        String file = path + fileName + "_" + System.currentTimeMillis() + ".xls";
        workbook.write(new File(file));
        workbook.close();


    }
}
