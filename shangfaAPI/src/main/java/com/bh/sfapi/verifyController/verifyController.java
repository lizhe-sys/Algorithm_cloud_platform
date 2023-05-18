package com.bh.sfapi.verifyController;

import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.entity.Condition;
import com.bh.sfapi.entity.QueryFields;
import com.bh.sfapi.entity.shangfa.DataMgr;
import com.bh.sfapi.entity.shangfa.FaultCase;
import com.bh.sfapi.entity.shangfa.User;
import com.bh.sfapi.entity.shangfa.dto.FaultCaseDto;
import com.bh.sfapi.result.RestMessage;
import com.bh.sfapi.service.DockerApi;
import com.bh.sfapi.service.DockerApiImpl;
import com.bh.sfapi.service.MeasurementApi;
import com.bh.sfapi.service.MeasurementApiImpl;
import com.bh.sfapi.service.shangfa.*;
import com.bh.sfapi.utils.ExcelUtil;
import com.bh.sfapi.utils.GetData;
import com.github.dockerjava.api.DockerClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/6/27 11:13
 * @desc
 */

@EnableAutoConfiguration
@Api(tags = "指标验证操作")
@RestController
@RequestMapping("verify/performance")
@CrossOrigin
@Slf4j
public class verifyController {

    //docker连接地址
    @Value("${dockerUrl}")
    private String dockerUrl;
    // 远程连接ip
    @Value("${remoteIp}")
    private String remoteIp;
    // 远程连接用户名
    @Value("${remoteName}")
    private String remoteName;
    // 远程连接密码
    @Value("${remotePwd}")
    private String remotePwd;

    // 容器启动路径
    @Value("${jupyterStartDir}")
    private String jupyterStartDir;
    // 标准模型路径
    @Value("${stdModelDir}")
    private String stdModelDir;
    // 容器挂载主目录
    @Value("${backupPath}")
    private String backupPath;
    @Value("${jupyterBackupPath}")
    private String jupyterBackupPath;

    // 镜像名称
    @Value("${imageName}")
    private String imageName;
    @Value("${jupyterImageName}")
    private String jupyterImageName;

    @Value("${influxdbUrl}")
    private String influxdbUrl;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${secondDataDataBase}")
    private String secondDataDataBase;

    @Autowired
    DockerApi dockerApi = new DockerApiImpl();
    @Autowired
    MeasurementApi measurementApi = new MeasurementApiImpl();
    @Autowired
    DataMgrService dataMgrService = new DataMgrServiceImpl();
    @Autowired
    UserServiceApi userServiceApi = new UserServiceImpl();
    @Autowired
    FaultCaseService faultCaseService;
    @Autowired
    HealthMgrService healthMgrService;
    @Autowired
    MapMgrService mapMgrService;



    /**
     * 每个数据模型，都使用docker容器运行，支持不少于100个数据模型的分布式处理;提供linux后台启动100个docker容器的截图；
     * @return
     */
    @ApiOperation("数据并发计算能力")
    @GetMapping("test1")
    public RestMessage test1(){

        backupPath = jupyterBackupPath;
        imageName = jupyterImageName;
        List<String> containerList = new ArrayList<>();
        for( int i = 0; i < 100; i++ ){
            int userId = 20000 + i;
            String container = dockerApi.createContainer( remoteIp, remoteName, remotePwd, backupPath, imageName, userId, 1 , 1024 , 1 );
            containerList.add( container );
        }

        return new RestMessage( containerList );

    }

//    @ApiOperation("删除数据并发计算能力验证样例")
//    @GetMapping("deleteTest1")
//    public RestMessage deleteTest1(){
//
//        backupPath = jupyterBackupPath;
//
//        for (int i = 0; i < 100; i++) {
//            int userId = 20000 + i;
//            String containerName = "jupyter-"+userId ;
//            DockerClient dockerClient = dockerApi.getConnect(dockerUrl);
//            // 再删除容器,及挂载到主机的目录
//            dockerApi.deleteContainer( dockerClient , containerName , remoteIp ,remoteName , remotePwd , backupPath );
//
//        }
//        return new RestMessage();
//    }

    /**
     * 使用数据工程对试验器参数信息进行结构化管理，支持1000个以上的数据工程；建立1000个数据工程并截图；
     * @return
     */
    @ApiOperation("参数管理能力")
    @GetMapping("test2")
    public RestMessage test2(){

        List<User> allUser = userServiceApi.getUsers();
        User user = new User();
        if( allUser == null ){
            return new RestMessage();
        }
        user = allUser.get( 0 );
        for( int i = 0; i < 1000; i++ ){
            DataMgr dataMgr = new DataMgr();
            // 设置创建时间
            dataMgr.setCreateTime( new Date() );
            // 设置为试验数据类型
            dataMgr.setDataType( 0 );
            dataMgr.setMotorType("CJ1000");
            dataMgr.setInflxudbDatabase("EMUGroundData");
            dataMgr.setInflxudbMeasurement("EMUGroudDataCheck1");
            dataMgr.setSecondMeasurement( "testSecondMeasurement" );
            dataMgr.setMotorNo( "C1A01" );
            dataMgr.setFirstField( "MotorNo" );
            dataMgr.setDataMgrName("数据工程验证-" + i );
            dataMgr.setUserId( user.getUserId() );
            dataMgr.setCreateTime( new Date() );
            dataMgr.setEndTime( new Date() );
            dataMgr.setFinished( 1 );
            // 将试验数据记录保存在mysql
            dataMgrService.save( dataMgr );
        }

        return new RestMessage( "成功创建1000条数据工程");

    }

//    @ApiOperation("删除参数管理能力测试样例")
//    @GetMapping("deleteTest2")
//    public RestMessage deleteTest2(){
//        dataMgrService.deleteTest2();
//        return new RestMessage( );
//
//    }


    /**
     * 知识管理里面的故障案例，模拟5000条，搜索的时候小于1秒；提供截图；
     * @return
     */
    @ApiOperation("算法验证要求-g")
    @GetMapping("test15")
    public RestMessage test15(){
        List<FaultCaseDto> faultCaseDtos = faultCaseService.queryFaultCaseList();
        if( faultCaseDtos.size() == 0 ){
            return new RestMessage();
        }
        FaultCase faultCase = new FaultCase();
        faultCase.setUserId( faultCaseDtos.get( 0 ).getUserId() );
        faultCase.setHealthMgrId( faultCaseDtos.get( 0 ).getHealthMgrId() );
        faultCase.setMapMgrId( faultCaseDtos.get( 0 ).getMapMgrId() );
        faultCase.setFaultStartTime( faultCaseDtos.get( 0 ).getFaultStartTime() );
        faultCase.setFaultEndTime( faultCaseDtos.get( 0 ).getFaultEndTime() );

        List<FaultCase> list = new ArrayList<>();
        for ( int i = 0; i < 5000; i++ ){
            FaultCase faultCaseInsert = new FaultCase();
            faultCaseInsert.setFaultCaseName( "故障案例验证-" + i );
            faultCaseInsert.setUserId( faultCase.getUserId() );
            faultCaseInsert.setHealthMgrId( faultCase.getHealthMgrId() );
            faultCaseInsert.setMapMgrId( faultCase.getMapMgrId() );
            faultCaseInsert.setFaultStartTime( faultCase.getFaultStartTime() );
            faultCaseInsert.setFaultEndTime( faultCase.getFaultEndTime() );
            list.add( faultCaseInsert );
        }
        faultCaseService.batchInsert( list );
        return new RestMessage( "成功创建5000条故障案例");

    }

    @ApiOperation("算法验证要求-g-1")
    @ApiImplicitParam(name = "faultCase", value = "faultcase", required = true)
    @PostMapping("test15_1")
    public RestMessage test15_1( @RequestBody FaultCaseDto faultCase ) {

        PageHelper.startPage( 1 , 8 );
        List<FaultCaseDto> faultCases = faultCaseService.dimQueryFaultCaseListByPage( faultCase );
        PageInfo pageInfo=new PageInfo(faultCases);
        // faultCases总数目
        int totals = (int) pageInfo.getTotal();
        // 查询分页页列表
        List faultCaseList = pageInfo.getList();
        Map< String , Object > result = new HashMap<>();
        result.put("totals" , totals );
        result.put("faultCases" , faultCaseList );
        return new RestMessage( result );

    }

    /**
     * 每个数据工程，可以配置1000个不同的监测参数（字段），支持超过1000个参数的处理能力；
     * @return
     */
    @ApiOperation("参数处理能力")
    @GetMapping("test7")
    public RestMessage test7(){
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        List<TreeMap<String, Object>> datas = new ArrayList<>();

        for (int i = 0; i < 1500; i++) {
            TreeMap<String, Object> map = new TreeMap<>();
            for (int j = 0; j < 200; j++) {
                map.put( "性能测试字段-" + j , 0.465667188167572 );
            }
            map.put( "time" , ( System.currentTimeMillis() +  8 * 60 * 60 * 1000 ) ) ;
            try {
                Thread.sleep( 1 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            datas.add( map );
        }

        List< TreeMap<String , Object > > batchValues = new ArrayList<>();
        TreeMap<String, String> tag = new TreeMap();
        for (int i = 0; i < datas.size(); i++) {
            TreeMap<String, Object> data = datas.get( i );
            batchValues.add( data );
            if( ( ( i+1) % 50 ) == 0 ){
                this.measurementApi.writeData(influxDB, "DataMgr", "test_200", batchValues, tag );
                batchValues.clear();
            }
        }
        this.measurementApi.writeData(influxDB, "DataMgr", "test_200", batchValues, tag );

        influxDB.close();
        return new RestMessage();

    }


    @ApiOperation("算法开发要求-性能要求")
    @ApiImplicitParam(name = "databaseName",value = "databaseName",required = true)
    @PostMapping("test8")
    public RestMessage test8( String databaseName , @RequestBody Condition condition ) {
        String sql = condition.getQuerySql();
        sql = sql.toLowerCase();
        if( sql.indexOf("delete") != -1 || sql.indexOf("create") != -1 || sql.indexOf("show") != -1
                || sql.indexOf("drop") != -1 || sql.indexOf("grant") != -1 || sql.indexOf("revoke") != -1
                || sql.indexOf("alter") != -1 || sql.indexOf("set") != -1 || sql.indexOf("kill") != -1 ){
            return new RestMessage( false , "sql语句含有含非法操作" );
        }
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        List<Object> res = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                JSONObject result = this.measurementApi.test8( influxDB , databaseName , condition.getQuerySql() );
//                res.add( result );
               log.info("{} , {}" , Thread.currentThread().getName() , result );
            } , "thread" + i ).start();
        }
        influxDB.close();
        return new RestMessage( res );

    }

}
