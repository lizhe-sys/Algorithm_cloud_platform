package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.ModelScore;

import java.util.List;

public interface ModelScoreSerivce {

    void addModelScore(ModelScore modelScore);

    void updateModelScore(ModelScore modelScore);

    ModelScore getModelScoreByUser(ModelScore modelScore);

    Float getAvgScore(Long stdModelId);

    List<ModelScore> getStdModelScoreList(Long stdModelId);
}
