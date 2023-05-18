package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.SynthesisDAO;
import com.bh.sfapi.entity.shangfa.Synthesis;
import com.bh.sfapi.entity.shangfa.dto.SynthesisInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/21 11:46
 * @desc
 */


@Service
@Transactional
public class SysthesisServiceImpl implements SynthesisService {


    @Autowired
    SynthesisDAO synthesisDAO;

    @Override
    public void save(Synthesis systhesis) {
        systhesis.setCreateTime( new Date() );
        synthesisDAO.save( systhesis );
    }

    @Override
    public void deleteSynthesisById(long synthesisId) {
        synthesisDAO.deleteSynthesisById( synthesisId );
    }

    @Override
    public List<Synthesis> querySynthesisListByPage() {
        return synthesisDAO.querySynthesisListByPage();
    }

    @Override
    public List<Synthesis> dimQuerySynthesisListByPage(Synthesis synthesis) {
        return synthesisDAO.dimQuerySynthesisListByPage( synthesis );
    }

    @Override
    public void updateSynthesis(Synthesis synthesis) {
        synthesisDAO.updateSynthesis( synthesis );
    }

    @Override
    public List<SynthesisInfoDto> synthesisInfo() {
        return synthesisDAO.synthesisInfo();
    }

    @Override
    public Synthesis getSynthesisById(long synthesisId) {
        return synthesisDAO.getSynthesisById( synthesisId );
    }
}
