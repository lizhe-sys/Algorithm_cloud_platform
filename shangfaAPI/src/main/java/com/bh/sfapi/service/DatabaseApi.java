package com.bh.sfapi.service;

import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;

import java.util.List;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/26 15:05
 * @desc
 */
public interface DatabaseApi {

    public QueryResult createDatabase(InfluxDB influxDB, String databaseName );

    public QueryResult dropDatabase( InfluxDB influxDB, String databaseName );

    public List<List<Object>> databaseList(InfluxDB influxDB );

}
