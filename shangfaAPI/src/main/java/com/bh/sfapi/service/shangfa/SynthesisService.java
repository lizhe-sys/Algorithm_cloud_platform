package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.Synthesis;
import com.bh.sfapi.entity.shangfa.dto.SynthesisInfoDto;

import java.util.List;

public interface SynthesisService {

    void save(Synthesis systhesis);

    void deleteSynthesisById(long synthesisId);

    List<Synthesis> querySynthesisListByPage();

    List<Synthesis> dimQuerySynthesisListByPage(Synthesis synthesis);

    void updateSynthesis(Synthesis synthesis);

    List<SynthesisInfoDto> synthesisInfo();

    Synthesis getSynthesisById(long synthesisId);
}
