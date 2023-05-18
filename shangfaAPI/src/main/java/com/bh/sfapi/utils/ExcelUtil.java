package com.bh.sfapi.utils;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/12 16:57
 * @desc
 */

import com.csvreader.CsvReader;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
            System.out.println( list );
            for (int j = 0; j < list.size(); j++) {
                String value = list.get(j);
                if ( value != null )
                    label = new Label(j, i+1, value );
                else
                    label = new Label(j, i+1, " ");
                sheet.addCell(label);
            }
        }
        workbook.write();
        workbook.close();
    }

    public static List<String> getData( String filePath ){
        //判断是否为csv文件
        if (filePath.substring(filePath.length()-4).equals(".csv")){
            ArrayList<String> strList = null;
            List< String > list = new ArrayList<>();
            try {
                ArrayList<String[]> arrList = new ArrayList<String[]>();
                strList = new ArrayList<String>();
//                CsvReader reader = new CsvReader("C:\\Users\\user\\Desktop\\EMUGroudDataCheck1.csv",',', Charset.forName("GBK"));
                CsvReader reader = new CsvReader(filePath,',',Charset.forName("GBK"));
                while (reader.readRecord()) {
//                System.out.println(Arrays.asList(reader.getValues()));
                    arrList.add(reader.getValues()); // 按行读取，并把每一行的数据添加到list集合
                }
                reader.close();
//                System.out.println("读取的行数：" + arrList.size());
//                System.out.println(arrList);
                // 如果要返回 String[] 类型的 list 集合，则直接返回 arrList
                // 以下步骤是把 String[] 类型的 list 集合转化为 String 类型的 list 集合
                for (int row = 1; row < arrList.size(); row++) {
                    // 组装String字符串
                    // 如果不知道有多少列，则可再加一个循环
                    String ele = arrList.get(row)[0] ;
//                    System.out.println(ele);
                    strList.add(ele);
                }
//                System.out.println(strList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return strList;
        }
//        为xls文件
        else{
            List< String > list = new ArrayList<>();
            try {
                //获得Excel文件
                File file = new File(filePath);
//                File file=new File("C:\\Users\\user\\Desktop\\EMUGroudDataCheck1.xls");

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
//                System.out.println( list );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }
            return list;
            }

    }



    public static void main(String[] args) {
//        getData("D:\\graduateCode\\商发\\trunck\\shangfaAPI\\src\\main\\java\\com\\bh\\sfapi\\fields\\CJ-2000-EMU-地检一型.xls");
        List< List<String> > list = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        fields.add("abc");
        fields.add( "123" );
        fields.add("你好吗");
        list.add( fields );
        System.out.println( list );
        String[] keys = {"key1"};
        try {
            write( list , keys , "tmp.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

