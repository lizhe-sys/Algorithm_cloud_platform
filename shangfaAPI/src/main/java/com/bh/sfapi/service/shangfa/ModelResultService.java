package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.entity.shangfa.ModelResult;

import java.util.Date;
import java.util.List;

public interface ModelResultService {

    public void createModelResultTable(String modelResultTableName);

    int checkTableIsExist(String modelResultTableName);

    void batchInsertData( List<String> cols , List<List<Object>> modelResults, String modelResultTableName);

    List<ModelResult> getModelResultByModelId(String modelResultTableName);

    List<ModelResult> getModelResultByXtime(String modelResultTableName, Date startTime, Date endTime);

    List<ModelResult> getModelResultByXFloat(String modelResultTableName, Float start, Float end);

}
