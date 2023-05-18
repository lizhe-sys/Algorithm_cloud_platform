package com.bh.sfapi.service.shangfa;


import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.dao.*;
import com.bh.sfapi.entity.shangfa.DataMgr;
import com.bh.sfapi.entity.shangfa.LogRecord;
import com.bh.sfapi.entity.shangfa.firstPage.MotorInfo;
import com.bh.sfapi.entity.shangfa.firstPage.FirstPage;
import com.bh.sfapi.entity.shangfa.firstPage.*;
import com.bh.sfapi.service.DatabaseApi;
import com.bh.sfapi.utils.HttpRequest;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class FirstPageServiceImpl implements FirstPageService {

    @Autowired
    FirstPageDAO firstPageDAO;

    @Autowired
    DataMgrDAO dataMgrDAO;

    @Autowired
    StdModelDAO stdModelDAO;

    @Autowired
    SynthesisDAO synthesisDAO;

    @Autowired
    ModelMgrDAO modelMgrDAO;

    @Autowired
    FaultCaseDAO faultCaseDAO;

    @Autowired
    MapMgrDAO mapMgrDAO;

    @Autowired
    HealthMgrDAO healthMgrDAO;

    @Autowired
    LogRecordDAO logRecordDAO;

    @Autowired
    DatabaseApi databaseApi;

    @Autowired
    ModelCallDAO modelCallDAO;

    @Value("${influxdb-host-ip}")
    private String influxdbHostIp;
    @Value("${influxdb-checksize-port}")
    private String influxdbCheckSizePort;

    @Value("${influxdbUrl}")
    private String influxdbUrl;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${secondDataDataBase}")
    private String secondDataDataBase;


    @Override
    public FirstPage firstPageInfo() {

        // 首先更新总数据量大小
        FirstPage firstPage = new FirstPage();
        String databaseStr = dataBases();
        float size = computeDataSize( databaseStr );
        // 检查数据量的接口无异常
        if( size != -1 ){
            firstPage.setDataMgr_data_size( size );
            firstPageDAO.updatefirstPage( firstPage );
        }
        // 再进行首页信息的查询
        return firstPageDAO.firstPageInfo();
    }

    @Override
    public void updatefirstPage(FirstPage firstPage) {
        firstPageDAO.updatefirstPage( firstPage );
    }

    // 数据库列表拼接为字符串
    private String dataBases(){
        InfluxDB influxDB = InfluxDBFactory.connect(influxdbUrl , username , password);
        List<List<Object>> databaseList = this.databaseApi.databaseList(influxDB);

        StringBuilder databaseStr = new StringBuilder();
        for (int i = 0; i < databaseList.size(); i++) {
            List<Object> objects = databaseList.get(i);
            String dataBase = (String) objects.get( 0 );
            if (secondDataDataBase.equals(dataBase) == false && "_internal".equals(dataBase) == false) {
                databaseStr.append( dataBase );
                databaseStr.append("--");
            }
        }
        return String.valueOf(databaseStr);
    }

    // 计算数据管理列表的总时长
    private float computeTotalTime(List<DataMgr> dataMgrs ){
        float res = 0;
        try {
            for (int i = 0; i < dataMgrs.size(); i++) {
                DataMgr dataMgr = dataMgrs.get(i);
                Date startTime = dataMgr.getStartTime();
                Date endTime = dataMgr.getEndTime();
                long time1 = startTime.getTime();
                long time2 = endTime.getTime();
                res += ( double )( time2 - time1 ) / 1000.0 / 60.0 / 60.0;
            }
        }catch ( Exception e ){
            return 0;
        }
        return (float)(Math.round( res * 1000 )) / 1000;
    }

    // 获得influxdb数据数据量大小
    private float computeDataSize( String databaseStr ){
        HttpRequest httpRequest = new HttpRequest();
        String url = "http://" + influxdbHostIp + ":" + influxdbCheckSizePort + "/file/fileSize?databaseStr=" + databaseStr;
        String res = "";
        float size = 0;
        try {
            res = httpRequest.sendGet( url , "" );
            JSONObject obj = (JSONObject) JSONObject.parse(res);
            size = Float.parseFloat( ( obj.get("size") ).toString() );
        }
        catch ( Exception e ){
            return -1;
        }
        return size;
    }

    @Override
    public void updatefirstPageByType(int type) {

        // 更新数据管理内容
        if( type == 1 ){
            FirstPage firstPage = new FirstPage();
            List<TypeCountDto> typeCounts = dataMgrDAO.getTypeCount();
            for (int i = 0; i < typeCounts.size(); i++) {
                TypeCountDto typeCount = typeCounts.get(i);
                if( typeCount.getType() == 0 ){
                    firstPage.setDataMgr_data( typeCount.getNum() );
                }
                if( typeCount.getType() == 1 ){
                    firstPage.setDataMgr_video( typeCount.getNum() );
                }
                if( typeCount.getType() == 2 ){
                    firstPage.setDataMgr_picture( typeCount.getNum() );
                }
                if( typeCount.getType() == 3 ){
                    firstPage.setDataMgr_other( typeCount.getNum() );
                }
            }

            if( firstPage.getDataMgr_data() == null ){
                firstPage.setDataMgr_data( 0 );
            }
            if( firstPage.getDataMgr_video() == null ){
                firstPage.setDataMgr_video( 0 );
            }
            if( firstPage.getDataMgr_picture() == null ){
                firstPage.setDataMgr_picture( 0 );
            }
            if( firstPage.getDataMgr_other() == null ){
                firstPage.setDataMgr_other( 0 );
            }

            List<DataMgr> dataMgrs = dataMgrDAO.getDataMgrTimeList();
            float totalTime = computeTotalTime( dataMgrs );
            firstPage.setDataMgr_data_time( totalTime );
            String databaseStr = dataBases();
            float size = computeDataSize( databaseStr );
            if( size != -1 )
                firstPage.setDataMgr_data_size( size );
            firstPageDAO.updatefirstPage( firstPage );
            return;
        }

        // 更新模型仓库统计数据
        if( type == 2 ){
            List<TypeCountDto> typeCounts = stdModelDAO.getTypeCount();
            FirstPage firstPage = new FirstPage();
            for (int i = 0; i < typeCounts.size(); i++) {
                TypeCountDto typeCount = typeCounts.get(i);
                if( typeCount.getType() == 1 ){
                    firstPage.setStdModel_online( typeCount.getNum() );
                }
                if( typeCount.getType() == 2 ){
                    firstPage.setStdModel_package( typeCount.getNum() );
                }
            }

            if( firstPage.getStdModel_online() == null ){
                firstPage.setStdModel_online( 0 );
            }
            if( firstPage.getStdModel_package() == null ){
                firstPage.setStdModel_package( 0 );
            }
            firstPageDAO.updatefirstPage( firstPage );
            return;
        }

        // 更新综合知识统计数据
        if( type == 3 ){
            List<TypeCountDto> typeCounts = synthesisDAO.getTypeCount();
            FirstPage firstPage = new FirstPage();
            for (int i = 0; i < typeCounts.size(); i++) {
                TypeCountDto typeCount = typeCounts.get(i);
                if( typeCount.getType() == 1 ){
                    firstPage.setKnowledge_file( typeCount.getNum() );
                }
                if( typeCount.getType() == 2 ){
                    firstPage.setKnowledge_picture( typeCount.getNum() );
                }
                if( typeCount.getType() == 3 ){
                    firstPage.setKnowledge_video( typeCount.getNum() );
                }
            }

            if( firstPage.getKnowledge_file() == null )
                firstPage.setKnowledge_file( 0 );
            if( firstPage.getKnowledge_picture() == null )
                firstPage.setKnowledge_picture( 0 );
            if( firstPage.getKnowledge_video() == null )
                firstPage.setKnowledge_video( 0 );

            firstPageDAO.updatefirstPage( firstPage );
            return;
        }
        // 更新故障案例统计数据
        if( type == 4 ){
            List<TypeCountDto> typeCounts = faultCaseDAO.getTypeCount();
            FirstPage firstPage = new FirstPage();
            for (int i = 0; i < typeCounts.size(); i++) {
                TypeCountDto typeCount = typeCounts.get(i);
                firstPage.setKnowledge_faultcase( typeCount.getNum() );
            }
            firstPageDAO.updatefirstPage( firstPage );
            return;
        }
        // 更新图谱统计数据
        if( type == 5 ){
            List<TypeCountDto> typeCounts = mapMgrDAO.getTypeCount();
            FirstPage firstPage = new FirstPage();
            for (int i = 0; i < typeCounts.size(); i++) {
                TypeCountDto typeCount = typeCounts.get(i);
                if( typeCount.getType() == 1 ){
                    firstPage.setMap_data( typeCount.getNum() );
                }
                if( typeCount.getType() == 2 ){
                    firstPage.setMap_model( typeCount.getNum() );
                }
            }

            if( firstPage.getMap_data() == null )
                firstPage.setMap_data( 0 );
            if( firstPage.getMap_model() == null )
                firstPage.setMap_model( 0 );
            firstPageDAO.updatefirstPage( firstPage );
            return;
        }
        // 更新试验工程统计数据
        if( type == 6 ){
            List<TypeCountDto> typeCounts = healthMgrDAO.getTypeCount();
            FirstPage firstPage = new FirstPage();
            for (int i = 0; i < typeCounts.size(); i++) {
                TypeCountDto typeCount = typeCounts.get(i);
                firstPage.setHealth_count( typeCount.getNum() );
            }
            firstPageDAO.updatefirstPage( firstPage );
            return;
        }
    }

    @Override
    public Map<String, Object> firstPageStdModelInfo(int year ) {

        // 在线模型统计信息
        List<StdModelInfo> stdModel_online = stdModelDAO.firstPageStdModelInfo( year , 1 );
        // 封装模型统计信息
        List<StdModelInfo> stdModel_package = stdModelDAO.firstPageStdModelInfo( year , 2 );
        // 模型调用每月的统计信息
        List<StdModelInfo> stdModel_modelCall = modelCallDAO.firstPageModelCallInfo( year );

        int count = 12;

        int[] months = new int[ count ];
        int[] stdModel = new int[ count ];
        int[] onlionModel = new int[ count ];
        int[] packageModel = new int[ count ];
        int[] callCount = new int[ count ];

        for (int i = 0; i < stdModel_online.size(); i++) {
            StdModelInfo online = stdModel_online.get( i );
            int month = online.getMonth() , num = online.getNum();
            onlionModel[ month - 1 ] += online.getNum();
            stdModel[ month - 1 ] += online.getNum();
        }

        for (int i = 0; i < stdModel_package.size(); i++) {
            StdModelInfo pack = stdModel_package.get( i );
            int month = pack.getMonth() , num = pack.getNum();
            packageModel[ month - 1 ] += pack.getNum();
            stdModel[ month - 1 ] += pack.getNum();
        }

        for ( int i = 0;  i < stdModel_modelCall.size(); i++ ){
            StdModelInfo modelCall = stdModel_modelCall.get(i);
            int month = modelCall.getMonth()  , call = modelCall.getCallCount();
            callCount[ month - 1 ] += call;
        }

        for( int i = 0; i < count; i++ ){
            months[ i ] = i+1;
        }

        Map< String , Object > res = new HashMap<>();
        res.put("months" , months );
        res.put("online" , onlionModel );
        res.put("package" , packageModel );
        res.put("stdmodel" , stdModel );
        res.put("callcount", callCount );
        return res;
    }

    @Override
    public List<ModelMgrInfo> modelMgrInfo() {
        return modelMgrDAO.modelMgrInfo();
    }

    @Override
    public List<DataMgrMapCount> dataMgrMapCount() {
        return dataMgrDAO.dataMgrMapCount();
    }

    @Override
    public List<FaultCaseRecordCount> faultCaseRecordCount() {
        return faultCaseDAO.faultCaseRecordCount();
    }

    @Override
    public List<LogRecord> logInfo() {
        return logRecordDAO.logInfo();
    }

    @Override
    public DataMgrInfo firstPageDataMgrInfo(MotorInfo motorInfo) {
        DataMgr dataMgr = new DataMgr();
        dataMgr.setInflxudbDatabase( motorInfo.getMotorType() );
        dataMgr.setInflxudbMeasurement( motorInfo.getSubjectType() );
        dataMgr.setMotorNo( motorInfo.getMotorNo() );

        List<DataMgr> dataMgrs = dataMgrDAO.dimQueryDataMgrTimeList( dataMgr );

        // 计算总时长
        float totalTime = computeTotalTime(dataMgrs);
        DataMgrInfo dataMgrInfo = new DataMgrInfo();
        dataMgrInfo.setDataMgr_data_time( totalTime );
        // 总数目
        dataMgrInfo.setDataMgr_data( dataMgrs.size() );
        // 数据量大小
        if( motorInfo.getMotorType() != null ){
            float size = computeDataSize(motorInfo.getMotorType());
            dataMgrInfo.setDataMgr_data_size( size );
        }
        return dataMgrInfo;
    }

    @Override
    public Map<String, List> firstPageMotorInfo() {
        List<String> motorType = dataMgrDAO.getInfluxdbDataBase();
        List<String> subjectType = dataMgrDAO.getInfluxdbMeasurement();
        List<String> motorNo = dataMgrDAO.getInfluxdbMotorNo();

        Map<String , List > motorInfo = new HashMap<>();
        motorInfo.put("motorType" , motorType );
        motorInfo.put("subjectType" , subjectType );
        motorInfo.put("motorNo" , motorNo );

        return motorInfo;
    }
}
