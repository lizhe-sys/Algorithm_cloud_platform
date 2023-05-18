package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.dao.ModelScoreDAO;
import com.bh.sfapi.entity.shangfa.ModelScore;
import com.bh.sfapi.entity.shangfa.StdModel;
import com.bh.sfapi.entity.shangfa.firstPage.StdModelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModelScoreSerivceImpl implements ModelScoreSerivce{

    @Autowired
    ModelScoreDAO modelScoreDAO;
    @Autowired
    StdModelService stdModelService;

    @Override
    public void addModelScore(ModelScore modelScore) {

        // 新增评分
        modelScoreDAO.addModelScore( modelScore );
        // 获取该模型最新打分信息
        StdModelInfo scoreInfo = modelScoreDAO.getModelScoreInfo(modelScore.getStdModelId());
        // 将最新平均分更新到标准模型内
        StdModel stdModel = new StdModel();
        stdModel.setStdModelId( modelScore.getStdModelId() );
        stdModel.setScore( scoreInfo.getAvgScore() );
        stdModel.setMinScore( scoreInfo.getMinScore() );
        stdModel.setMaxScore( scoreInfo.getMaxScore() );
        stdModelService.updateStdModel( stdModel );

    }

    @Override
    public void updateModelScore(ModelScore modelScore) {
        // 更新评分
        modelScoreDAO.updateModelScore( modelScore );
        // 获取该模型最新打分信息
        StdModelInfo scoreInfo = modelScoreDAO.getModelScoreInfo(modelScore.getStdModelId());
        // 将最新平均分更新到标准模型内
        StdModel stdModel = new StdModel();
        stdModel.setStdModelId( modelScore.getStdModelId() );
        stdModel.setScore( scoreInfo.getAvgScore() );
        stdModel.setMinScore( scoreInfo.getMinScore() );
        stdModel.setMaxScore( scoreInfo.getMaxScore() );
        stdModelService.updateStdModel( stdModel );
    }

    @Override
    public ModelScore getModelScoreByUser(ModelScore modelScore) {
        return modelScoreDAO.getModelScoreByUser( modelScore );
    }

    @Override
    public Float getAvgScore(Long stdModelId) {
        return modelScoreDAO.getAvgScore( stdModelId );
    }

    @Override
    public List<ModelScore> getStdModelScoreList(Long stdModelId) {
        return modelScoreDAO.getStdModelScoreList( stdModelId );
    }
}
