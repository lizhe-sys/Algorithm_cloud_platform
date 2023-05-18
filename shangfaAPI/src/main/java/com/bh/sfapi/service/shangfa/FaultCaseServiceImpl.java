package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.dao.FaultCaseDAO;
import com.bh.sfapi.entity.shangfa.FaultCase;
import com.bh.sfapi.entity.shangfa.dto.FaultCaseDto;
import com.bh.sfapi.entity.shangfa.dto.HealthMgrDto;
import com.bh.sfapi.service.MeasurementApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FaultCaseServiceImpl implements FaultCaseService{

    @Value("${influxdbUrl}")
    private String influxdbUrl;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${mysqlDatabase}")
    private String mysqlDatabase;
    @Value("${uploadAppendixDir}")
    private String uploadAppendixDir;
    @Value("${secondDataDataBase}")
    private String secondDataDataBase;

    @Autowired
    FaultCaseDAO faultCaseDAO;
    @Autowired
    HealthMgrService healthMgrService;
    @Autowired
    MapMgrService mapMgrService;
    @Autowired
    DataMgrService dataMgrService;
    @Autowired
    MeasurementApi measurementApi;

    private long timeDelta = (long)( 8 * 60 * 60 * 1000 );

    @Override
    public void save(FaultCase faultCase) {

        faultCase.setCreateTime( new Date() );
        faultCase.setRecordCount( 0.0 );
        // 将信息保存
        faultCaseDAO.save( faultCase );
    }

    @Override
    public void batchInsert(List<FaultCase> list) {
        faultCaseDAO.batchInsert( list );
    }

    @Override
    public FaultCaseDto queryFaultCaseById(long faultCaseId) {
        FaultCaseDto faultCase = faultCaseDAO.queryFaultCaseById( faultCaseId );
        if( faultCase == null ) return null;
        HealthMgrDto healthMgr = healthMgrService.getHealthMgrDtoById( faultCase.getHealthMgrId()  );
        if( healthMgr == null ) return null;
        faultCase.setDataMgrId( healthMgr.getDataMgrId() );
        faultCase.setDataMgrName( healthMgr.getDataMgrName() );
        faultCase.setHealthMgrName( healthMgr.getHealthMgrName() );
        faultCase.setModelMgrId( healthMgr.getModelMgrId() );
        faultCase.setModelName( healthMgr.getModelName() );
        return faultCase;
    }

    @Override
    public void deleteFaultCaseById(long faultCaseId) {
        faultCaseDAO.deleteFaultCaseById( faultCaseId );
    }

    @Override
    public List<FaultCaseDto> queryFaultCaseList() {
        return faultCaseDAO.queryFaultCaseList();
    }

    @Override
    public List<FaultCaseDto> dimQueryFaultCaseListByPage(FaultCaseDto faultCase) {
        return faultCaseDAO.dimQueryFaultCaseListByPage( faultCase );
    }

    @Override
    public void updateFaultCase(FaultCase faultCase) {
        faultCaseDAO.updateFaultCase( faultCase );
    }

    @Override
    public List<FaultCase> queryFaultCaseListByMapMgrId(long mapMgrId) {
        return faultCaseDAO.queryFaultCaseListByMapMgrId(mapMgrId);
    }

    @Override
    public FaultCase faultCaseByMapMgrId(Long mapMgrID) {
        return faultCaseDAO.faultCaseByMapMgrId(mapMgrID);
    }
}
