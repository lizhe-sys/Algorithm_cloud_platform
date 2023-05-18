package com.bh.sfapi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.entity.Condition;
import com.bh.sfapi.entity.QueryFields;
import com.bh.sfapi.entity.shangfa.DataMgr;
import com.bh.sfapi.result.RestMessage;
import com.bh.sfapi.service.DatabaseApi;
import com.bh.sfapi.service.DatabaseApiImpl;
import com.bh.sfapi.service.MeasurementApi;
import com.bh.sfapi.service.MeasurementApiImpl;
import com.bh.sfapi.service.shangfa.DataMgrService;
import com.bh.sfapi.utils.CSVUtil;
import com.bh.sfapi.utils.GetData;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @version 1.0
 * @create 2021/12/16 22:21
 * @desc
 * 数据记录返回的时间全是influxdb内时间
 * 具体处理方式：
 * 返回结果 2022-04-28T12:00:09Z ---> 前端处理之后应该需要显示为 2022-04-28 12:00:09
 * 上述两类时间时区相差8，但是需要处理为数字相同
 *
 * 数据记录查询：
 * 代码已处理加上 timeDelta，所以查询的参数直接按照北京时间进行查询即可
 * 查询参数  2022-04-28 12:00:09  经过处理之后 查询的实际为 2022-04-28T12:00:09Z的数据记录
 */

@EnableAutoConfiguration
@Api(tags = "influxdb数据记录操作")
@RequestMapping("base/record")
@RestController
@CrossOrigin
@Slf4j
public class RecordController {

    //用来解决influxdb数据加了八个小时的问题
    private long timeDelta = (long)( 8 * 60 * 60 * 1000 );

    // 容器挂载主目录
    @Value("${backupPath}")
    private String backupPath;
    @Value("${jupyterBackupPath}")
    private String jupyterBackupPath;
    @Value("${vscodeBackupPath}")
    private String vscodeBackupPath;

    @Value("${influxdbUrl}")
    private String influxdbUrl;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${secondDataDataBase}")
    private String secondDataDataBase;

    //宏定义线程
    Map<Long, Thread> map =new HashMap<>();

    @Autowired
    DatabaseApi databaseApi = new DatabaseApiImpl();
    @Autowired
    MeasurementApi measurementApi = new MeasurementApiImpl();
    @Autowired
    DataMgrService dataMgrService;


    @ApiOperation("数据计数")
    @PostMapping("countData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataMgrId",value = "dataMgrId",required = true) ,
            @ApiImplicitParam(name = "containerName",value = "containerName",required = true),
            @ApiImplicitParam(name = "modelMgrId",value = "modelMgrId",required = true),
            @ApiImplicitParam(name = "isStdModel",value = "isStdModel",required = true)
    })
    public RestMessage countData(Long dataMgrId, String containerName, Long modelMgrId, int isStdModel, @RequestBody Condition condition ) {
        String sql = condition.getQuerySql();
        /**
         * 进行安全性检查
         */
        sql = sql.toLowerCase();
        if( sql.indexOf("delete") != -1 || sql.indexOf("create") != -1 || sql.indexOf("show") != -1
                || sql.indexOf("drop") != -1 || sql.indexOf("grant") != -1 || sql.indexOf("revoke") != -1
                || sql.indexOf("alter") != -1 || sql.indexOf("set") != -1 || sql.indexOf("kill") != -1 ){
            return new RestMessage( false , "sql语句含有含非法操作" );
        }
        // 禁止*查询
        Pattern pattern = Pattern.compile("select\\s{0,}[*]\\s{0,}from");
        Matcher matcher = pattern.matcher(sql);
        if( matcher.find() == true ){
            return new RestMessage( false , "sql语句含有含非法操作" );
        }
        /**
         * 通过dataMgrId查询出数据模块相关信息
         */
        DataMgr dataMgr = dataMgrService.queryDataMgrById(dataMgrId);
        String database = dataMgr.getInflxudbDatabase();
        String measurement = dataMgr.getInflxudbMeasurement();
        String motorNo = dataMgr.getMotorNo();
        String firstField = dataMgr.getFirstField();
        Date startTime = dataMgr.getStartTime();
        Date endTime = dataMgr.getEndTime();
        int finished = dataMgr.getFinished();

        /**
         * 确定sql查询的起始和截止时间
         * 选定的时间范围 [a , b] ， 与 dataMgr的时间范围[x ，y] 的选择
         * start = a > x ? a : x
         * end = b < y ? b : y
         */
        Long start = ( startTime.getTime() + timeDelta ) * 1000000;
        Long end = null;
        if( finished == 1 && endTime != null ){
            end = ( endTime.getTime() + timeDelta ) * 1000000;
        }
        // 确定数据起始时间
        if( condition.getStartTime() != null ){
            startTime = startTime.getTime() > condition.getStartTime().getTime() ? startTime : condition.getStartTime();
            start = ( startTime.getTime() + timeDelta ) * 1000000;
        }
        // 确定数据截止时间
        if( condition.getEndTime() != null ){
            // 导入未完成
            if( finished == 0 ){
                endTime = condition.getEndTime();
            }
            else{
                endTime = endTime.getTime() < condition.getEndTime().getTime() ? endTime : condition.getEndTime();
            }
            end = ( endTime.getTime() + timeDelta ) * 1000000;
        }
        // 将sql 改造为真实查询语句
        double count = 0;
        // 查询出限定条件下的数据行数
        String countsql = "select count("+" \"" +firstField+"\""+" ) from "+" \"" +measurement+"\"" +" where time >=" +start;
        if( endTime != null ){
            countsql = countsql + " and time <= " + end;
        }
        if( motorNo != null ){
            countsql = countsql + " and MotorNo = "  + '\'' + motorNo + "\'";
        }

        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        JSONObject dataCount = measurementApi.queryDataBySql( influxDB, database, countsql );
        influxDB.close();
        List value=(List) dataCount.get("values");
        List val = (List) value.get( 0 );
        count = (double) val.get( 1 );
        JSONObject res = new JSONObject();
        res.put("count",count);
        return new RestMessage( res );
    }

    @ApiOperation("查数据")
    @PostMapping("queryData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataMgrId",value = "dataMgrId",required = true) ,
            @ApiImplicitParam(name = "containerName",value = "containerName",required = true),
            @ApiImplicitParam(name = "modelMgrId",value = "modelMgrId",required = true),
            @ApiImplicitParam(name = "isStdModel",value = "isStdModel",required = true)
    })
    public RestMessage queryData( Long dataMgrId, String containerName, Long modelMgrId, int isStdModel, @RequestBody Condition condition ) {
        String sql = condition.getQuerySql();
        String querySql = condition.getQuerySql();

        /**
         * 进行安全性检查
          */
        sql = sql.toLowerCase();
        if( sql.indexOf("delete") != -1 || sql.indexOf("create") != -1 || sql.indexOf("show") != -1
                || sql.indexOf("drop") != -1 || sql.indexOf("grant") != -1 || sql.indexOf("revoke") != -1
                || sql.indexOf("alter") != -1 || sql.indexOf("set") != -1 || sql.indexOf("kill") != -1 ){
            return new RestMessage( false , "sql语句含有含非法操作" );
        }
        // 禁止*查询
        Pattern pattern = Pattern.compile("select\\s{0,}[*]\\s{0,}from");
        Matcher matcher = pattern.matcher(sql);
        if( matcher.find() == true ){
            return new RestMessage( false , "sql语句含有含非法操作" );
        }

        /**
         * 通过dataMgrId查询出数据模块相关信息
         */
        DataMgr dataMgr = dataMgrService.queryDataMgrById(dataMgrId);
        String database = dataMgr.getInflxudbDatabase();
        String measurement = dataMgr.getInflxudbMeasurement();
        String motorNo = dataMgr.getMotorNo();
        String firstField = dataMgr.getFirstField();
        Date startTime = dataMgr.getStartTime();
        Date endTime = dataMgr.getEndTime();
        int finished = dataMgr.getFinished();

        /**
         * 确定sql查询的起始和截止时间
         * 选定的时间范围 [a , b] ， 与 dataMgr的时间范围[x ，y] 的选择
         * start = a > x ? a : x
         * end = b < y ? b : y
         */
        Long start = ( startTime.getTime() + timeDelta ) * 1000000;
        Long end = null;
        if( finished == 1 && endTime != null ){
            end = ( endTime.getTime() + timeDelta ) * 1000000;
        }
        // 确定数据起始时间
        if( condition.getStartTime() != null ){
            startTime = startTime.getTime() > condition.getStartTime().getTime() ? startTime : condition.getStartTime();
            start = ( startTime.getTime() + timeDelta ) * 1000000;
        }
        // 确定数据截止时间
        if( condition.getEndTime() != null ){
            // 导入未完成
            if( finished == 0 ){
                endTime = condition.getEndTime();
            }
            else{
                endTime = endTime.getTime() < condition.getEndTime().getTime() ? endTime : condition.getEndTime();
            }
            end = ( endTime.getTime() + timeDelta ) * 1000000;
        }
        // 将sql 改造为真实查询语句
        sql = sql.substring( 0 , sql.indexOf("from") );
        sql = sql + " from " +'\"'+ measurement + '\"'+" where time >= " + start;
        if( endTime != null ){
            sql = sql + " and time <= " + end;
        }
        if( motorNo != null ){
            sql = sql + " and MotorNo = "  + '\'' + motorNo + "\'";
        }
//        System.out.println(sql);

        /**
         * 进行数据复制的数量控制
         */
        String[] filedSplit = sql.split(",");
        int fieldCount = filedSplit.length;
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        double count = 0;
        int limitCount = 0;
        // 查询出限定条件下的数据行数
        String queryCountSql = sql.substring( 0 , sql.indexOf("select") + 6 ) +" \"" +firstField + "\" " + sql.substring( sql.indexOf( "from") , sql.length() );
        queryCountSql = "select count( " + "\"" +firstField+"\" ) from ( " + queryCountSql + " )";
//        log.info("condition={}", condition );
//        log.info("sql={}", sql );
//        log.info("queryCountSql={}", queryCountSql );
        JSONObject dataCountJson = measurementApi.queryDataBySql( influxDB, database, queryCountSql );
        List values = (List)dataCountJson.get("values");
        if( values != null ){
            List value = (List) values.get( 0 );
            if( value != null ){
                count = (double) value.get( 1 );
            }
            // 限制在1000000个单元格
            if( count * fieldCount <= 10000000 ){
                limitCount = (int)count;
            }
            else {
//                limitCount = 10000000 / fieldCount;
                limitCount=(int)count;
                if( sql.contains("limit") ){

                    sql = sql.substring(0, sql.indexOf("limit") + 6 ) + limitCount;
                }else {
                    sql = sql + " limit " + limitCount;
                }

            }
        }
        log.info("count={} , fieldCount={} ， limitCount={}", count , fieldCount , limitCount );


        /**
         * 数据的查询及保存操作
         */

        int index = querySql.indexOf("from");
        if( index < 0 )
            index = querySql.indexOf("FROM");
        querySql = querySql.substring( 0 , index ) + sql.substring( sql.indexOf("from") , sql.length() );
//        log.info("sql={}" , sql );
//        log.info("querySql={}" , querySql );
        backupPath = jupyterBackupPath;
        JSONObject result = this.measurementApi.queryData(influxDB, database, measurement, querySql );
        influxDB.close();
        if (result.get("columns") == null) {
            return new RestMessage( null );
        }
        else {
            List valueList = (List)result.get("values");
            List value = (List)valueList.get(valueList.size() - 1);
            // 查询出最后一条数据记录
            String lastTime = (String) value.get( 0 );

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
            String filename =  dateFormat.format(new Date()) + ".csv";

            String filePath = "";

            // 保存在标准模型数据查询文件夹下
            if( isStdModel == 1 ){
//                filePath = "d:\\" + filename;
                filePath = backupPath + containerName +"/stdmodel/" + modelMgrId + "/querydata/" + filename;
            }
            // 模型调用文件夹下
            else
                filePath = backupPath + containerName +"/" + modelMgrId + "/querydata/" + filename;
            //使用线程，实现中断
//            @GET("/export")
//                    public Long start(){
//
//            }
            String finalFilePath = filePath;
//            Thread thread =new Thread(()->{
                CSVUtil.wirteCSV(finalFilePath, result );
//            });
//            thread.start();

            JSONObject res = new JSONObject();
            res.put("fileName" , filename );
            res.put("count",count);
            res.put("lastTime" , lastTime );
//            res.put("ID",thread.getId());
            return new RestMessage( res );
        }

    }
    //中断查数据
    @GetMapping("queryDataStop")
    public void queryDataStop(Long id){
        Thread thread = map.get(id);
//        thread.stop();
    }
    //查看线程状态
    @GetMapping("查看线程状态")
    public int status(Long id){
        Thread thread = map.get(id);
        Thread.State state = thread.getState();
        while(state != Thread.State.TERMINATED){//只要线程不终止，就一直输出状态
            try {
                Thread.sleep(100);
                state = thread.getState();//更新线程状态
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 1;
    }

    @ApiOperation("根据sql语句获取数据")
    @ApiImplicitParam(name = "databaseName",value = "databaseName",required = true)
    @PostMapping("queryDataBySql")
    public RestMessage queryDataBySql( String databaseName , @RequestBody Condition condition ) {
        String sql = condition.getQuerySql();
        sql = sql.toLowerCase();
        if( sql.indexOf("delete") != -1 || sql.indexOf("create") != -1 || sql.indexOf("show") != -1
            || sql.indexOf("drop") != -1 || sql.indexOf("grant") != -1 || sql.indexOf("revoke") != -1
            || sql.indexOf("alter") != -1 || sql.indexOf("set") != -1 || sql.indexOf("kill") != -1 ){
            return new RestMessage( false , "sql语句含有含非法操作" );
        }
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        JSONObject result = this.measurementApi.queryDataBySql( influxDB , databaseName , condition.getQuerySql() );
        influxDB.close();
        return new RestMessage( result );

    }



    @ApiOperation("写数据")
    @ApiOperationSupport( order = 1 )
    @ApiImplicitParams({@ApiImplicitParam(name = "databaseName",value = "databaseName",required = true) ,
            @ApiImplicitParam(name = "measurement",value = "measurement",required = true),
            @ApiImplicitParam(name = "tagValue",value = "tagValue",required = true)})
    @GetMapping("writeData")
    public RestMessage writeData(String databaseName, String measurement , String tagValue , Integer count ) {
        List<TreeMap<String, Object>> datas = GetData.getDataFromExcel( System.getProperty("user.dir") + "/src/main/java/com/bh/sfapi/fields/CJ-1000-EMU-地检一型.xls" , count );
//        List<TreeMap<String, Object>> datas = GetData.getDataFromExcel( "/root/sf/sfapi/fields/CJ-1000-EMU-地检一型.xls" , count );
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
//        List<TreeMap<String, Object>> datas = new ArrayList<>();
//        String[] keys = {"负载电压（U）" , "阶段平均电流（I）" , "线电压（U）" , "线电流（I）" , "电动机输出功率（P2）" , "振动测点1",
//                            "振动测点2" , "振动测点3" , "振动测点4" , "转速" };
//        for (int i = 0; i < 10; i++) {
//            TreeMap<String, Object> map = new TreeMap<>();
//            for (int j = 0; j < keys.length; j++) {
//                map.put( keys[ j ] , new Random().nextInt( 10 ) );
//            }
//            map.put( "time" , ( System.currentTimeMillis() +  8 * 60 * 60 * 1000 ) ) ;
//            try {
//                Thread.sleep( 1 );
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            datas.add( map );
//        }

        List< TreeMap<String , Object > > batchValues = new ArrayList<>();
        TreeMap<String, String> tag = new TreeMap();
        tag.put("MotorNo", tagValue );
        for (int i = 0; i < datas.size(); i++) {
            TreeMap<String, Object> data = datas.get( i );
            batchValues.add( data );
            if( ( ( i+1) % 50 ) == 0 ){
                this.measurementApi.writeData(influxDB, databaseName, measurement, batchValues, tag );
                batchValues.clear();
            }
        }
        this.measurementApi.writeData(influxDB, databaseName, measurement, batchValues, tag );

        influxDB.close();
        return new RestMessage();
    }


    @ApiOperation("实时数据")
    @PostMapping("queryRealtimeData")
    @ApiImplicitParams({@ApiImplicitParam(name = "databaseName",value = "databaseName",required = true) ,
            @ApiImplicitParam(name = "measurement",value = "measurement",required = true) ,
            @ApiImplicitParam(name = "tagKey",value = "tagKey",required = true),
            @ApiImplicitParam(name = "tagValue",value = "tagValue",required = true ),
            @ApiImplicitParam(name = "frequency",value = "frequency",required = true)
    })
    public RestMessage queryRealtimeData(String databaseName, String measurement, String tagKey ,String tagValue, int frequency,@RequestBody QueryFields queryFields ) {

        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        JSONObject result = measurementApi.queryRealtimeData(influxDB, databaseName, measurement, tagKey, tagValue, frequency,queryFields);
        influxDB.close();
        return new RestMessage( result );
    }

    @ApiOperation("历史数据")
    @PostMapping("queryHistoryData")
    @ApiImplicitParams({@ApiImplicitParam(name = "databaseName",value = "databaseName",required = true) ,
            @ApiImplicitParam(name = "measurement",value = "measurement",required = true) ,
            @ApiImplicitParam(name = "tagKey",value = "tagKey",required = true),
            @ApiImplicitParam(name = "tagValue",value = "tagValue",required = true ),
            @ApiImplicitParam(name = "startTime",value = "startTime",required = true),
            @ApiImplicitParam(name = "endTime",value = "endTime",required = true) })
    public RestMessage queryHistoryData(String databaseName, String measurement, String tagKey ,String tagValue, Long startTime, Long endTime , @RequestBody QueryFields queryFields ) {
        //对于influxdb入库数据减了八个小时进行操作
        startTime = startTime + timeDelta;
        endTime = endTime + timeDelta;
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        JSONArray results = measurementApi.queryHistoryData(influxDB, databaseName, measurement, tagKey, tagValue, startTime, endTime, queryFields);
        influxDB.close();
        return new RestMessage( results );
    }

}
