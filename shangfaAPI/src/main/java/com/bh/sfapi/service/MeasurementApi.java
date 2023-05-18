package com.bh.sfapi.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.entity.QueryFields;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;

import java.util.List;
import java.util.TreeMap;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/26 15:05
 * @desc
 */
public interface MeasurementApi {

    public QueryResult writeData(InfluxDB influxDB, String databaseName, String measurement , List< TreeMap< String  ,Object > > values, TreeMap<String  ,String > tags );

    public QueryResult copyDataToInfluxdb(InfluxDB influxDB, String databaseName, String measurement ,String targetDataBase, String targetMeasurement ,String tagKey, String tagValue, Long startTime, Long endTime, QueryFields queryFields  );

    public JSONObject queryData(InfluxDB influxDB, String databaseName, String measurement, String querySql);

    public List<List<Object>> queryMeasurementList(InfluxDB influxDB, String databaseName);

    public List<List<Object>> queryFields(InfluxDB influxDB, String database, String measurement);

    public QueryResult deleteMeasurement(InfluxDB influxDB, String database, String measurement);

    public JSONObject queryDataBySql( InfluxDB influxDB, String database, String sql);

    public List< String > getTagKeys(InfluxDB influxDB, String databaseName, String measurement);

    public List<String> getTagValues(InfluxDB influxDB, String databaseName, String measurement, String tagKey);

    public JSONArray queryHistoryData( InfluxDB influxDB , String databaseName, String measurement, String tagKey, String tagValue, Long startTime, Long endTime, QueryFields queryFields);

    public JSONObject queryRealtimeData(InfluxDB influxDB, String databaseName, String measurement, String tagKey, String tagValue, int secondNum, QueryFields queryFields);

    public JSONArray queryHistoryDataLimitCount( InfluxDB influxDB , String databaseName, String measurement, String tagKey, String tagValue, Long startTime, Long endTime, int count, QueryFields queryFields);

    JSONObject test8(InfluxDB influxDB, String databaseName, String querySql);

    JSONObject gettimeBySql(InfluxDB influxDB, String database, String sql2);


    Object queryDataBySql2(InfluxDB influxDB, String database, String sql);
}

