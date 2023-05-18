package com.bh.sfapi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.result.RestMessage;
import com.bh.sfapi.service.DatabaseApi;
import com.bh.sfapi.service.DatabaseApiImpl;
import com.bh.sfapi.service.MeasurementApi;
import com.bh.sfapi.service.MeasurementApiImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2021/12/16 22:14
 * @desc
 */

@EnableAutoConfiguration
@Api(tags = "influxdb表操作")
@RequestMapping("base/measurement")
@RestController
@CrossOrigin
@Slf4j
public class MeasurementController {


    // 容器挂载主目录
    @Value("${backupPath}")
    private String backupPath;
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


    public RestMessage databaseList() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        List<List<Object>> databaseList = this.databaseApi.databaseList(influxDB);
        influxDB.close();
        return new RestMessage( databaseList );
    }

    @ApiOperation("查询表列表")
    @ApiImplicitParam(name = "databaseName",value = "databaseName",required = true)
    @GetMapping("queryMeasurementList")
    public RestMessage queryMeasurementList(String databaseName) {
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        List<List<Object>> lists = this.measurementApi.queryMeasurementList(influxDB, databaseName);
        influxDB.close();
        return new RestMessage( lists );
    }

    @ApiOperation("获取数据库和表的树形结构数据")
    @GetMapping("queryTreeData")
    public RestMessage queryTreeData() {
        List<List<Object>> lists = (List<List<Object>>) this.databaseList().getData();
        JSONArray treeDatas = new JSONArray();

        for (int i = 1; i < lists.size(); ++i) {
            JSONObject treeData = new JSONObject();
            String databaseName = (String) ((List) lists.get(i)).get(0);
            treeData.put("label", databaseName);
            List<List<Object>> datas = (List<List<Object>>) this.queryMeasurementList(databaseName).getData();
            JSONArray measurements = new JSONArray();

            for (int j = 0; j < datas.size(); ++j) {
                JSONObject measurement = new JSONObject();
                measurement.put("label", ((List) datas.get(j)).get(0));
                measurement.put("database", databaseName);
                measurements.add(measurement);
            }

            treeData.put("children", measurements);
            treeDatas.add(treeData);
        }

        return new RestMessage(treeDatas);
    }

    @ApiOperation("获取表字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "databaseName",value = "databaseName",required = true) ,
            @ApiImplicitParam(name = "measurement",value = "measurement",required = true)})
    @GetMapping("queryFields")
    public RestMessage queryFields( String databaseName, String measurement) {
        JSONArray fields = new JSONArray();
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        List<List<Object>> lists = this.measurementApi.queryFields(influxDB, databaseName, measurement);

        for (int i = 0; i < lists.size(); ++i) {
            JSONObject field = new JSONObject();
            field.put("name", ((List) lists.get(i)).get(0));
            field.put("type", ((List) lists.get(i)).get(1));
            fields.add(field);
        }
        influxDB.close();
        return new RestMessage( fields );
    }

    @ApiOperation("获取表的tagKeys")
    @ApiImplicitParams({@ApiImplicitParam(name = "databaseName",value = "databaseName",required = true) ,
            @ApiImplicitParam(name = "measurement",value = "measurement",required = true)})
    @GetMapping("getTagKeys")
    public RestMessage getTagKeys( String databaseName , String measurement ){
        InfluxDB influxDB = InfluxDBFactory.connect( influxdbUrl , username , password );
        List<String> tagKeys = measurementApi.getTagKeys(influxDB, databaseName, measurement);
        influxDB.close();
        return new RestMessage( tagKeys );
    }

    @ApiOperation("获取表的tagValues")
    @ApiImplicitParams({@ApiImplicitParam(name = "databaseName",value = "databaseName",required = true) ,
            @ApiImplicitParam(name = "measurement",value = "measurement",required = true),
            @ApiImplicitParam(name = "tagKey",value = "tagKey",required = true)})
    @GetMapping("getTagValues")
    public RestMessage getTagValues( String databaseName , String measurement , String tagKey ){
        InfluxDB influxDB = InfluxDBFactory.connect( influxdbUrl , username , password );
        List<String> tagValues = measurementApi.getTagValues(influxDB, databaseName, measurement , tagKey );
        influxDB.close();
        return new RestMessage( tagValues );
    }


    @ApiOperation("删除指定表")
    @ApiImplicitParams({@ApiImplicitParam(name = "databaseName",value = "databaseName",required = true) ,
            @ApiImplicitParam(name = "measurement",value = "measurement",required = true)})
    @DeleteMapping("deleteMeasurement")
    public RestMessage deleteMeasurement( String databaseName , String measurement ) {
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        QueryResult queryResult = this.measurementApi.deleteMeasurement(influxDB, databaseName, measurement);
        influxDB.close();
        return new RestMessage( queryResult );
    }

}
