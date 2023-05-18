package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.ModelMgr;
import com.bh.sfapi.entity.shangfa.firstPage.ModelMgrInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ModelMgrDAO extends BaseDAO< ModelMgr, String > {

    ModelMgr getModelMgrById(String modelId);

    // 模糊查询
    List<ModelMgr> dimQueryModelMgr(ModelMgr modelMgr);

    List<ModelMgr> getModelMgrListByDataMgrId(@Param("dataMgrId") Long dataMgrId ,@Param("userId") Long userId );

    ModelMgr getNewModelMgr(Long userId);

    ModelMgr getModelMgrByName(String modelName);

    List<ModelMgr> getModelMgrListByStdModelId(Long stdModelId);

    List<ModelMgr> getSharedModelMgrList(Long userId);

    List<ModelMgrInfo> modelMgrInfo();

}
