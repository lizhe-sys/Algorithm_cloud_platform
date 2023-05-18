package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.KnowledgeMgrDAO;
import com.bh.sfapi.entity.shangfa.KnowledgeMgr;
import com.bh.sfapi.entity.shangfa.dto.KnowledgeMgrDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/2/21 16:37
 * @desc
 */

@Service
@Transactional
public class KnowledgeMgrServiceImpl implements KnowledgeMgrService {

    @Autowired
    KnowledgeMgrDAO knowledgeMgrDAO;

    @Override
    public void addKnowledgeMgr(KnowledgeMgr knowledgeMgr) {
        knowledgeMgrDAO.save( knowledgeMgr );
    }

    @Override
    public void updateKnowledgeMgr(KnowledgeMgr knowledgeMgr) {
        knowledgeMgrDAO.update( knowledgeMgr );
    }

    @Override
    public void deleteKnowledgeMgr(String knowledgeMgrId) {
        knowledgeMgrDAO.delete( knowledgeMgrId );
    }

    @Override
    public KnowledgeMgr getKnowledgeMgrById(String knowledgeMgrId) {
        KnowledgeMgr knowledgeMgr = knowledgeMgrDAO.getKnowledgeMgrById(knowledgeMgrId);
        return knowledgeMgr;
    }

    @Override
    public List<KnowledgeMgrDto> queryKnowledgeMgrList() {
        List<KnowledgeMgrDto> knowledgeMgrs = knowledgeMgrDAO.queryKnowledgeMgrList();
        return knowledgeMgrs;
    }

    @Override
    public List<KnowledgeMgrDto> dimQueryKnowledgeMgr(KnowledgeMgrDto knowledgeMgrDto) {
        List<KnowledgeMgrDto>  knowledgeMgrs = knowledgeMgrDAO.dimQueryKnowledgeMgr( knowledgeMgrDto );
        return knowledgeMgrs;
    }

}
