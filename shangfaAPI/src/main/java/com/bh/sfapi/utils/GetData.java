package com.bh.sfapi.utils;

import io.swagger.models.auth.In;

import java.util.*;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/29 14:05
 * @desc
 */
public class GetData {


    public static List< TreeMap<String , Object > > getDataFromExcel( String filePath , Integer count ){

        List< TreeMap<String , Object > > datas = new ArrayList<>();
        List<String> list = ExcelUtil.getData( filePath );
        Random random = new Random();
        int n = count == null ? 0 : count;
        int frontValue = 0;
//        System.out.println( list.size() );
        for (int i = 0; i < n; i++) {
            TreeMap<String , Object > data = new TreeMap<>();
            for (int j = 0; j < list.size(); j++) {
                data.put( list.get( j ) , random.nextFloat() );
            }
            data.put( "time" , ( System.currentTimeMillis() +  8 * 60 * 60 * 1000 ) ) ;
            try {
                Thread.sleep( 1 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            datas.add( data );
        }
//        System.out.println( datas );
        return datas;
    }

    public static void main(String[] args) {
        System.out.println( new Date().getTime()*1000000 );
    }
}
