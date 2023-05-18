package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.Synthesis;
import com.bh.sfapi.entity.shangfa.dto.SynthesisInfoDto;
import com.bh.sfapi.entity.shangfa.firstPage.TypeCountDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SynthesisDAO extends BaseDAO<Synthesis, String> {

    void deleteSynthesisById(long synthesisId);

    List<Synthesis> querySynthesisListByPage();

    List<Synthesis> dimQuerySynthesisListByPage(Synthesis synthesis);

    void updateSynthesis(Synthesis synthesis);

    List<SynthesisInfoDto> synthesisInfo();

    Synthesis getSynthesisById(long synthesisId);

    List<TypeCountDto> getTypeCount();

}
