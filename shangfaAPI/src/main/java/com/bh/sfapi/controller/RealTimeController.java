//package com.bh.sfapi.controller;
//
//import com.bh.sfapi.entity.RealTime;
//import com.bh.sfapi.result.Result;
//import com.bh.sfapi.service.RealTimeApi;
//import com.bh.sfapi.service.RealTimeApiImpl;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author Administrator
// * @version 1.0
// * @create 2021/12/15 16:08
// * @desc
// */
//
//
//@Api("商发mysql数据库")
//@RequestMapping("sf/mysql")
//@RestController
//@CrossOrigin
//@Slf4j
//public class RealTimeController {
//
//    @Autowired
//    RealTimeApi realTimeApi = new RealTimeApiImpl();
//
//
//    @ApiOperation("查询实时数据全部记录")
//    @GetMapping("findAll")
//    public Result findAll(){
//
//        log.info("查询实时数据全部记录");
//        List<RealTime> all = realTimeApi.findAll();
//        log.info("查询实时数据全部记录长度：{}" , all.size() );
//        return Result.ok( all );
//
//    }
//
//    @ApiOperation("查询当前最新记录")
//    @GetMapping("queryRealTimeRecord")
//    protected Result queryRealTimeRecord( String influxdb_database ){
//
//        log.info("查询来自：{}influxdb数据库，当前最新记录" , influxdb_database );
//        RealTime queryRealTimeRecord = realTimeApi.queryRealTimeRecord(influxdb_database);
//        return Result.ok( queryRealTimeRecord );
//    }
//
//    public static void main(String[] args) {
//        RealTimeController realTimeController = new RealTimeController();
//        realTimeController.findAll();
//    }
//
//}
