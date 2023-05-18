package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.ModelResultDAO;
import com.bh.sfapi.entity.shangfa.ModelResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/21 15:32
 * @desc
 */
@Service
@Transactional
public class ModelResultServiceImpl implements ModelResultService {

    @Autowired
    ModelResultDAO modelResultDAO;

    @Override
    public void createModelResultTable(String modelResultTableName) {
        modelResultDAO.createModelResultTable( modelResultTableName );
    }

    @Override
    public int checkTableIsExist(String modelResultTableName) {
        return modelResultDAO.checkTableIsExist( modelResultTableName );
    }

    @Override
    public void batchInsertData(List<String> cols  ,List<List<Object>> modelResults, String modelResultTableName) {
        modelResultDAO.batchInsertData( cols, modelResults, modelResultTableName );
    }

    @Override
    public List<ModelResult> getModelResultByModelId(String modelResultTableName) {
        return modelResultDAO.getModelResultByModelId( modelResultTableName );
    }

    @Override
    public List<ModelResult> getModelResultByXtime(String modelResultTableName, Date startTime, Date endTime) {
        return modelResultDAO.getModelResultByXtime( modelResultTableName,  startTime,  endTime);
    }

    @Override
    public List<ModelResult> getModelResultByXFloat(String modelResultTableName, Float start, Float end) {
        return modelResultDAO.getModelResultByXFloat( modelResultTableName , start , end );
    }
}
