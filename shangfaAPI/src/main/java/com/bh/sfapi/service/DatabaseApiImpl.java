package com.bh.sfapi.service;

import com.bh.sfapi.utils.QueryResultTool;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/26 15:10
 * @desc
 */
@Service
@Slf4j
public class DatabaseApiImpl implements DatabaseApi {

    @Override
    public QueryResult createDatabase( InfluxDB influxDB ,  String databaseName ) {
        influxDB.setDatabase(databaseName);
        String createDatabase = "CREATE DATABASE " + databaseName;
        QueryResult queryResult = influxDB.query(new Query(createDatabase));
        return queryResult;
    }

    @Override
    public QueryResult dropDatabase( InfluxDB influxDB , String databaseName) {
        String dropDatabase = "DROP DATABASE " + databaseName;
        QueryResult queryResult = influxDB.query( new Query( dropDatabase ) );
        return queryResult;
    }

    @Override
    public List<List<Object>> databaseList(InfluxDB influxDB ) {
        String databaseList = " SHOW DATABASES";
        QueryResult queryResult = influxDB.query( new Query( databaseList ) );
        List<List<Object>> values = QueryResultTool.getValues( queryResult );
        return values;
    }
}
