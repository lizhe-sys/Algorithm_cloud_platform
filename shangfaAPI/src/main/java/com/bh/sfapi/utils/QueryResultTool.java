package com.bh.sfapi.utils;

import org.influxdb.dto.QueryResult;

import java.util.List;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/30 17:00
 * @desc
 */
public class QueryResultTool {

    public static List<List<Object>> getValues( QueryResult queryResult ){
        List<List<Object>> values = null;
        List<QueryResult.Result> results = queryResult.getResults();
        if( results.size() > 0 ){
            List<QueryResult.Series> series = results.get(0).getSeries();
            if( series != null ){
                values = series.get(0).getValues();
            }
            else {
                return null;
            }
        }
        return values;
    }
    public static List<String> getColumns( QueryResult queryResult ){
        List<String> columns = null;
        List<QueryResult.Result> results = queryResult.getResults();
        if( results.size() > 0 ){
            List<QueryResult.Series> series = results.get(0).getSeries();
            if( series != null ){
                columns = series.get(0).getColumns();
            }
            else {
                return null;
            }
        }
        return columns;
    }
}
