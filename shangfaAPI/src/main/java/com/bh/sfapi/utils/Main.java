package com.bh.sfapi.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
//        File file = new File("D:\\SoftWare\\influxdb-1.6.2_windows_amd64\\influxdb-1.6.2-1");
//        long sizeOf = FileUtils.sizeOf(file);
//        System.out.println( (double) sizeOf / 1024.0 / 1024.0 / 1024.0  );
        String  s = "select d1 from test where time > 123 and time < 456 ";
        s = s.substring( 0 , s.indexOf("from") );
        System.out.println( s );
    }
}
