package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.ModelMgr;

import java.util.List;

public interface ModelMgrService {

    void addModelMgr(ModelMgr modelMgr);

    void updateModelMgr(ModelMgr modelMgr);

    void deleteModelMgr(String modelId);

    ModelMgr getModelMgrById(String modelId);

    // 查询modelMgr列表
    List<ModelMgr> getModelMgrList();

    // 模糊查询
    List<ModelMgr> dimQueryModelMgr(ModelMgr modelMgr);

    List<ModelMgr> getModelMgrListByDataMgrId(Long dataMgrId , Long userId );

    ModelMgr getNewModelMgr(Long userId);

    ModelMgr getModelMgrByName(String modelName);

    List<ModelMgr> getModelMgrListByStdModelId(Long stdModelId);

    List<ModelMgr> getSharedModelMgrList(Long userId);

}
