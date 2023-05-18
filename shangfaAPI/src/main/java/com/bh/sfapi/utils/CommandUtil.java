package com.bh.sfapi.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/30 0:12
 * @desc
 */
public class CommandUtil {

    private static String  DEFAULTCHART="UTF-8";

    public String  execute( String cmd ) {
        String[] strs = cmd.split(";");
        List<String> commands = Arrays.asList( strs );
        StringBuffer res = new StringBuffer();
        Runtime run = Runtime.getRuntime();
        try {
            Process proc = run.exec("/bin/bash", null, null);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream() , DEFAULTCHART ));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                out.println(line);
            }
            out.println("exit");// 这个命令必须执行，否则in流不结束。
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
//                System.out.println(rspLine);
                res.append( rspLine +"\n");
            }
            proc.waitFor();
            in.close();
            out.close();
            proc.destroy();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    public static void main(String[] args) {
//        CommandUtil commandUtil = new CommandUtil();
//        while ( true ){
//            Scanner sc = new Scanner( System.in );
//            String s = sc.nextLine();
//            String res = commandUtil.execute(s);
//            System.out.println( res );
//        }

//        String s = "123";
//        System.out.println( Arrays.asList( s.split(";") ) );
    }


}
