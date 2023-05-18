package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.FlyEye;
import com.bh.sfapi.entity.shangfa.HealthMgr;
import com.bh.sfapi.entity.shangfa.dto.HealthMgrDto;
import com.bh.sfapi.entity.shangfa.firstPage.TypeCountDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface HealthMgrDAO extends BaseDAO<HealthMgr, String > {


    HealthMgr getHealthMgrById(String healthMgrId);

    HealthMgr getHealthMgrByModelMgrId( long modelMgrId );

    public List<HealthMgrDto> getHealthMgrList();

    List<HealthMgrDto> dimQueryHealthMgr(HealthMgrDto healthMgrDto);

    void deleteHealthMgrByModelMgrId(String modelMgrId);

    HealthMgrDto getHealthMgrDtoById(long healthMgrId);

    HealthMgr getHealthMgrByUserId(Long userId);

    HealthMgr getHealthMgrByName(String healthMgrName);

    List<TypeCountDto> getTypeCount();

    void changeData(Long olddataMgrId, Long dataMgrId);

    void updateHealthMgr(HealthMgr healthMgr);

    void updatemap(Long olddataMgrId, Long dataMgrId);

    List<Long> getalldataId();

    void updateheal(Long healthMgrId, String string,String starttime);

    FlyEye find(Long healthMgrId);

    void updateFlyEyeNames(FlyEye flyEye);

    void saveFlyEyeNames(FlyEye flyEye);


    Long getdataMgrIdByhealthMgrId(Long healthMgrId);
}
