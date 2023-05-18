package com.bh.sfapi.controller;
import com.bh.sfapi.result.RestMessage;
import com.bh.sfapi.service.DatabaseApi;
import com.bh.sfapi.service.DatabaseApiImpl;
import com.bh.sfapi.service.MeasurementApi;
import com.bh.sfapi.service.MeasurementApiImpl;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@Api(tags = "influxdb数据库操作")
@RequestMapping("base/influxdb")
@RestController
@CrossOrigin
@Slf4j
public class InfluxdbController {

    @Value("${influxdbUrl}")
    private String influxdbUrl;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;



    @Autowired
    DatabaseApi databaseApi = new DatabaseApiImpl();
    @Autowired
    MeasurementApi measurementApi = new MeasurementApiImpl();



    @ApiOperation("创建数据库")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParam(name = "databaseName",value = "databaseName",required = true)
    @GetMapping("createDatabase")
    public RestMessage createDatabase(String databaseName) {
        InfluxDB influxDB = InfluxDBFactory.connect( influxdbUrl , username , password );
        QueryResult createDatabase = this.databaseApi.createDatabase( influxDB, databaseName);
        influxDB.close();
        return new RestMessage(createDatabase);
    }

    @ApiOperation("获取数据库列表")
    @GetMapping("databaseList")
    public RestMessage databaseList() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        List<List<Object>> databaseList = this.databaseApi.databaseList(influxDB);
        influxDB.close();
        return new RestMessage( databaseList );
    }


    @ApiOperation("删除数据库")
    @ApiImplicitParam(name = "databaseName",value = "databaseName",required = true)
    @DeleteMapping("dropDatabase")
    public RestMessage dropDatabase(String databaseName) {
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        QueryResult dropDatabase = this.databaseApi.dropDatabase(influxDB, databaseName);
        influxDB.close();
        return new RestMessage(dropDatabase);
    }





    public static void main(String[] args) {

        System.out.println( System.getProperty("user.dir"));
//        InfluxdbController influxdbController = new InfluxdbController();
//        influxdbController.getTagKeys( "EMUGroundData" , "EMUGroudDataCheck1");
    }
}
