package com.vip.file.utils;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/12 16:57
 * @desc
 */

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {


    public static void write( List<List<String>> lists, String[] keys, String filePath ) throws Exception {
        //创建Excel文件
        File file = new File(filePath);
        //创建文件
        file.createNewFile();
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        //创建sheet
        WritableSheet sheet = workbook.createSheet("sheet1", 0);
        //添加数据
        Label label = null;
        for (int i = 0; i < keys.length; i++) {
            label = new Label( i, 0, keys[i]);
            sheet.addCell(label);
        }
        //追加数据
        //i行 j列
        for (int i = 0; i < lists.size(); i++) {
            List<String> list = lists.get( i );
            for (int j = 0; j < list.size(); j++) {
                String value = list.get(j);
                if ( value != null )
                    label = new Label(j, i+1, value );
                else
                    label = new Label(j, i + 1, " ");
                sheet.addCell(label);
            }
        }
        workbook.write();
        workbook.close();
    }

    public static List<String> getData( String filePath ){
        List< String > list = new ArrayList<>();
        try {
            //获得Excel文件
            File file = new File(filePath);
            //获得工作薄
            Workbook workbook = Workbook.getWorkbook(file);
            //获得第一个sheet
            Sheet sheet = workbook.getSheet(0);
            //获取数据
            for(int i = 1; i < sheet.getRows(); i++) {
                Cell cell = sheet.getCell(0, i);
                list.add( cell.getContents() );
//                System.out.println(cell.getContents() );
            }
//            System.out.println( list );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        getData("D:\\graduateCode\\商发\\trunck\\shangfaAPI\\src\\main\\java\\com\\bh\\sfapi\\fields\\CJ-2000-EMU-地检一型.xls");
    }
}

