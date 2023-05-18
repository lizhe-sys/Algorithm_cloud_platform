package com.bh.sfapi.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.entity.QueryFields;
import com.bh.sfapi.utils.QueryResultTool;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/26 15:10
 * @desc
 */
@Service
@Slf4j
public class MeasurementApiImpl implements MeasurementApi {


    @Override
    public QueryResult writeData(InfluxDB influxDB, String databaseName, String measurement , List< TreeMap< String  ,Object > > values , TreeMap<String  ,String > tag ) {
        influxDB.setDatabase( databaseName );

        // 首先需要获取InfluxDB
        BatchPoints batchPoints = BatchPoints.database(databaseName)
                .build();
        for (int j = 0; j < values.size(); j++) {
            TreeMap<String , Object > value = values.get( j );
            Point point1 = Point.measurement( measurement )
                    .time( (Long) value.get("time") , TimeUnit.MILLISECONDS )
                    .fields( value )
                    .tag( tag )
                    .build();
            batchPoints.point(point1);
        }
        influxDB.write( batchPoints );
        return null;
    }

    @Override
    public QueryResult copyDataToInfluxdb(InfluxDB influxDB, String databaseName, String measurement, String targetDataBase, String targetMeasurement,String tagKey, String tagValue, Long startTime, Long endTime, QueryFields queryFields) {
        influxDB.setDatabase( databaseName );
        String fieldStr = "";
        List<String> fields = queryFields.getFields();
        for (int i = 0; i < fields.size(); i++) {
            if ( i == 0 ){
                fieldStr =  "\"" + fields.get( i ) + "\"";
            }
            else {
                fieldStr = fieldStr + "," +  "\"" + fields.get( i ) + "\"";
            }
        }
        String querySql = "select " + fieldStr + " into " + targetDataBase +   ".." + targetMeasurement  +" from " + "\"" + measurement + "\""
                // 限定时间范围
                + " where time >= " + startTime + " and time <= " + endTime
                // tag条件筛选
                + " limit 1 ";
//        System.out.println(querySql);
        QueryResult queryResult = influxDB.query(new Query(querySql));
//        System.out.println(queryResult);
        return null;
    }

    // 需要新增tag限定
    @Override
    public JSONObject queryData(InfluxDB influxDB, String databaseName, String measurement, String querySql) {
        influxDB.setDatabase( databaseName );
        QueryResult queryResult = influxDB.query(new Query( querySql ));
        JSONObject result = new JSONObject();
        List< List<Object> > values = QueryResultTool.getValues( queryResult );
        List<String> columns = QueryResultTool.getColumns(queryResult);
        result.put("values" , values );
        result.put("columns" , columns );
        return result;
    }


    @Override
    public List<List<Object>> queryMeasurementList(InfluxDB influxDB, String databaseName) {
        influxDB.setDatabase( databaseName );
        System.out.println(databaseName);
        String querySql = "show measurements";
        QueryResult queryResult = influxDB.query(new Query(querySql));
        List<List<Object>> values = QueryResultTool.getValues( queryResult );
//        System.out.println("qr:"+queryResult);
//        System.out.println("vlaue:"+values);
        return values;

    }

    @Override
    public List<List<Object>> queryFields(InfluxDB influxDB, String database, String measurement) {
        influxDB.setDatabase( database );
        String querySql = "show field keys from " + "\"" + measurement + "\"";

        QueryResult queryResult = influxDB.query(new Query(querySql));
        List<List<Object>> values = QueryResultTool.getValues( queryResult );
        return values;
    }

    @Override
    public QueryResult deleteMeasurement(InfluxDB influxDB, String databaseName, String measurement) {
        influxDB.setDatabase( databaseName );
        String querySql = "drop measurement " + "\"" + measurement + "\"";

        QueryResult queryResult = influxDB.query(new Query(querySql));
        return queryResult;
    }

    @Override
    public JSONObject gettimeBySql(InfluxDB influxDB, String database, String sql2) {
        influxDB.setDatabase( database );
        QueryResult queryResult = influxDB.query(new Query(sql2));
        List<String> columns = QueryResultTool.getColumns(queryResult);
        List<List<Object>> values = QueryResultTool.getValues(queryResult);
        List<List<Object>> a=values;
        JSONObject result = new JSONObject();
        result.put("fields" , columns );
        result.put("values" , values );
        return result;
    }

    @Override
    public Object queryDataBySql2(InfluxDB influxDB, String database, String sql) {
        influxDB.setDatabase( database );
        QueryResult queryResult = influxDB.query(new Query(sql));
        List<List<Object>> values = QueryResultTool.getValues(queryResult);
        List<Object> va =values.get(0);
        Object res= va.get(1);
        return res ;

    }

    // 需要新增tag限定，需要和前面的那个sql进行调换，这个直接传入条件后台生成sql
    @Override
    public JSONObject queryDataBySql(InfluxDB influxDB, String database, String sql) {

        influxDB.setDatabase( database );
//        System.out.println(sql);
        QueryResult queryResult = influxDB.query(new Query(sql));
//        System.out.println(queryResult);
        List<String> columns = QueryResultTool.getColumns(queryResult);
//        System.out.println("6666"+queryResult.getResults());

        List<List<Object>> values = QueryResultTool.getValues(queryResult);
        List<List<Object>> a=values;
        //判断其小数位数
//        for (int i=0;i<values.size();i++){
//
//            Double t= (Double) (values.get(i)).get(1);
//            BigDecimal two = new BigDecimal(t);
//            double three = two.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//
//        }
//        System.out.println("4444"+QueryResultTool.getValues(queryResult));
        JSONObject result = new JSONObject();
        result.put("fields" , columns );
        result.put("values" , values );
        return result;
    }


    @Override
    public List<String> getTagKeys(InfluxDB influxDB, String databaseName, String measurement) {

        influxDB.setDatabase( databaseName );
        String querySql = "show tag keys from " + "\"" + measurement + "\"";
        QueryResult queryResult = influxDB.query(new Query(querySql));
        List<List<Object>> values = QueryResultTool.getValues(queryResult);
        List<String> tagKeys = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            tagKeys.add( (String) values.get( i ).get( 0 ) );
        }
        return tagKeys;
    }

    @Override
    public List<String> getTagValues(InfluxDB influxDB, String databaseName, String measurement, String tagKey) {
        influxDB.setDatabase( databaseName );
        String querySql = "show tag values from " + "\"" + measurement + "\" with key = " + tagKey;
        QueryResult queryResult = influxDB.query(new Query(querySql));
        List<List<Object>> values = QueryResultTool.getValues(queryResult);
        List<String> tagValues = new ArrayList<>();
        if( values != null ){
            for (int i = 0; i < values.size(); i++) {
                tagValues.add( (String) values.get( i ).get( 1 ) );
            }
        }
//        System.out.println(queryResult);
        return tagValues;
    }

    @Override
    public JSONArray queryHistoryData(InfluxDB influxDB, String databaseName, String measurement,  String tagKey, String tagValue, Long startTime, Long endTime, QueryFields queryFields) {
        influxDB.setDatabase( databaseName );

        String fieldStr = "";
        List<String> fields = queryFields.getFields();
        for (int i = 0; i < fields.size(); i++) {
            if ( i == 0 ){
                fieldStr =  "\"" + fields.get( i ) + "\"";
            }
            else {
                fieldStr = fieldStr + "," +  "\"" + fields.get( i ) + "\"";
            }
        }
        String querySql = "select " + fieldStr + " from " + "\"" + measurement + "\""
                        // 限定时间范围
                        + " where time > " + startTime + " and time < " + endTime
                        // tag条件筛选
                        + " and " + "\"" + tagKey + "\"= " + " \'" + tagValue + "\'";

        QueryResult queryResult = influxDB.query(new Query(querySql));
        List<String> columns = QueryResultTool.getColumns(queryResult);
        List<List<Object>> values = QueryResultTool.getValues(queryResult);

        JSONArray results = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            JSONObject result = new JSONObject();
            for (int j = 0; j < values.get(i).size(); j++) {
                if( "time".equals( columns.get( j ) ) ){
                    String timeStamp = getTimeStamp((String) values.get(i).get(j));
                    result.put( columns.get( j ) , timeStamp );
                }
                else {
                    result.put( columns.get( j ) , values.get(i).get( j ) );
                }
            }
            results.add( result );
        }
        return results;
    }

    @Override
    public JSONObject queryRealtimeData(InfluxDB influxDB, String databaseName, String measurement, String tagKey, String tagValue, int frequency, QueryFields queryFields) {

        influxDB.setDatabase( databaseName );

        String fieldStr = "";
        List<String> fields = queryFields.getFields();
        for (int i = 0; i < fields.size(); i++) {
            if ( i == 0 ){
                fieldStr =  "\"" + fields.get( i ) + "\"";
            }
            else {
                fieldStr = fieldStr + "," +  "\"" + fields.get( i ) + "\"";
            }
        }
        String querySql = "select " + fieldStr + " from " + "\"" + measurement + "\""
                // 限定时间范围
                + " where " + "\"" + tagKey + "\"= " + " \'" + tagValue + "\' ORDER BY time DESC LIMIT " + frequency;

        QueryResult queryResult = influxDB.query(new Query(querySql));

        List<String> columns = QueryResultTool.getColumns(queryResult);
        List<List<Object>> values = QueryResultTool.getValues(queryResult);

        JSONObject result = new JSONObject();
        result.put("fields" , columns );
        result.put("values" , values );
        return result;
    }

    @Override
    public JSONArray queryHistoryDataLimitCount(InfluxDB influxDB, String databaseName, String measurement, String tagKey, String tagValue, Long startTime, Long endTime, int count, QueryFields queryFields) {

        influxDB.setDatabase( databaseName );
        String fieldStr = "";
        List<String> fields = queryFields.getFields();
        for (int i = 0; i < fields.size(); i++) {
            if ( i == 0 ){
                fieldStr =  "\"" + fields.get( i ) + "\"";
            }
            else {
                fieldStr = fieldStr + "," +  "\"" + fields.get( i ) + "\"";
            }
        }
        String querySql = "select " + fieldStr + " from " + "\"" + measurement + "\""
                // 限定时间范围
                + " where time > " + startTime + " and time < " + endTime
                // tag条件筛选
                + " and " + "\"" + tagKey + "\"= " + " \'" + tagValue + "\' "
                + " limit " + count;

        QueryResult queryResult = influxDB.query(new Query(querySql));
        List<String> columns = QueryResultTool.getColumns(queryResult);
        List<List<Object>> values = QueryResultTool.getValues(queryResult);

        JSONArray results = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            JSONObject result = new JSONObject();
            for (int j = 0; j < values.get(i).size(); j++) {
                if( "time".equals( columns.get( j ) ) ){
                    String timeStamp = getTimeStamp((String) values.get(i).get(j));
                    result.put( columns.get( j ) , timeStamp );
                }
                else {
                    result.put( columns.get( j ) , values.get(i).get( j ) );
                }
            }
            results.add( result );
        }

        return results;
    }

    @Override
    public JSONObject test8(InfluxDB influxDB, String databaseName, String querySql) {
        influxDB.setDatabase( databaseName );
        long start = System.currentTimeMillis();
        QueryResult queryResult = influxDB.query(new Query(querySql));
        long end = System.currentTimeMillis();
        JSONObject result = new JSONObject();
        result.put("time" , end - start );
        return result;
    }



    public String getTimeStamp( String time ){
        time = time.replace("T", " ");
        time = time.replace("Z", "");
        return time;
    }

    public static void main(String[] args) {
        MeasurementApiImpl measurementApi = new MeasurementApiImpl();
        InfluxDB influxDB = InfluxDBFactory.connect( "http://101.37.34.176:8086/" , "admin" , "admin");
        String sql = "SELECT count( \"B通道采集的油门杆角度\" ) FROM \"EMUGroudDataCheck5\";" ;
        JSONObject jsonObject = measurementApi.queryDataBySql(influxDB, "EMUGroundData", sql);
        System.out.println(jsonObject );
    }
}
