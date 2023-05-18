package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.ModelResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ModelResultDAO extends BaseDAO<ModelResult, String >{

    public void createModelResultTable(@Param("modelResultTableName") String modelResultTableName );

    int checkTableIsExist(String modelResultTableName);

    void batchInsertData(@Param( "cols" ) List<String> cols, @Param("modelResults")List<List<Object>> modelResults, @Param("modelResultTableName")String modelResultTableName);

    List<ModelResult> getModelResultByModelId( @Param( "modelResultTableName" )String modelResultTableName);

    List<ModelResult> getModelResultByXtime( @Param("modelResultTableName")String modelResultTableName, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<ModelResult> getModelResultByXFloat( @Param("modelResultTableName") String modelResultTableName,  @Param("start") Float start,  @Param("end") Float end);
}

