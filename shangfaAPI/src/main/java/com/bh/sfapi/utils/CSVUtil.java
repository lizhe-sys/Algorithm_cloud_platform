package com.bh.sfapi.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.List;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/12/2 14:31
 * @desc
 */
public class CSVUtil {

    public static List<String> readCSV( String filePath ){

        try {
            BufferedReader reader = new BufferedReader(new FileReader( filePath ));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while((line=reader.readLine())!=null){
                //CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String item[] = line.split("，");
                //这就是你要的数据了
                String last = item[item.length-1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void wirteCSV( String filePath , JSONObject datas ){
        int row = 0;
        List<String> columnList = (List<String>) datas.get("columns");
        StringBuilder columns = new StringBuilder("Cid");
        for (int i = 0; i < columnList.size(); i++) {
            columns.append(",");
            columns.append( columnList.get(i) );
        }
        List< List<Object> > values = (List<List<Object>>) datas.get("values");
        try {
            // CSV数据文件
            File csv = new File(filePath );
//
            FileWriter writer= new FileWriter(csv,false);
            // 附加
            BufferedWriter bw = new BufferedWriter(writer);

//            BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(new FileOutputStream("\"C:\\Users\\user\\Desktop\\hadoop.csv\""),"UTF-8"));
            // 添加新的数据行
            bw.write( columns.toString() );
            bw.newLine();
            for (int i = 0; i < values.size(); i++) {
                List<Object> valueList = values.get(i);
                ++row;
                StringBuilder value = new StringBuilder( row + "" );
                for (int j = 0; j < valueList.size(); j++) {
                    value.append(",");
                    value.append( valueList.get(j) );
                }
                bw.write( value.toString() );
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
}


    public static void main(String[] args) {

    }
}
