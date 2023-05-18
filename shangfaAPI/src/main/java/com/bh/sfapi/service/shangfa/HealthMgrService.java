package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.FlyEye;
import com.bh.sfapi.entity.shangfa.HealthMgr;
import com.bh.sfapi.entity.shangfa.dto.HealthMgrDto;

import java.util.List;
import java.util.Map;

public interface HealthMgrService {

    void addHealthMgr(HealthMgr healthMgr);



    void deleteHealthMgr(String healthMgrId);

    HealthMgr getHealthMgrById(String healthMgrId);

    // 查询HealthMgr列表
    List<HealthMgrDto> getHealthMgrList();

    HealthMgr getHealthMgrByModelMgrId( long modelMgrId );

    // 模糊查询
    List<HealthMgrDto> dimQueryHealthMgr(HealthMgrDto healthMgrDto );

    void deleteHealthMgrByModelMgrId(String modelMgrId);

    HealthMgrDto getHealthMgrDtoById( long healthMgrId );

    HealthMgr getHealthMgrByUserId(Long userId);

    HealthMgr getHealthMgrByName(String healthMgrName);

    void changeData(Long olddataMgrId, Long dataMgrId);

    void updateHealthMgr(HealthMgr healthMgr);

    void updatemap(Long olddataMgrId, Long dataMgrId);

    List<Long> getalldataId();

    void update(Long healthMgrId, String string,String starttime);

    FlyEye find(Long healthMgrId);

    void updateFlyEyeNames(FlyEye flyEye);

    void saveFlyEyeNames(FlyEye flyEye);

    Map<String, Object> tomap(FlyEye flyEye);

    Long getdataMgrIdByhealthMgrId(Long healthMgrId);

    String text(String path);
}
