package com.bh.sfapi.dao;


import com.bh.sfapi.entity.shangfa.ModelScore;
import com.bh.sfapi.entity.shangfa.firstPage.StdModelInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelScoreDAO extends BaseDAO<ModelScore, String >{

    void addModelScore(ModelScore modelScore);

    void updateModelScore(ModelScore modelScore);

    ModelScore getModelScoreByUser(ModelScore modelScore);

    Float getAvgScore(Long stdModelId);

    List<ModelScore> getStdModelScoreList(Long stdModelId);

    StdModelInfo getModelScoreInfo(Long stdModelId);

}
