package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.KnowledgeMgr;
import com.bh.sfapi.entity.shangfa.dto.KnowledgeMgrDto;

import java.util.List;

public interface KnowledgeMgrService {

    void addKnowledgeMgr(KnowledgeMgr knowledgeMgr);

    void updateKnowledgeMgr(KnowledgeMgr knowledgeMgr);

    void deleteKnowledgeMgr(String knowledgeMgrId);

    KnowledgeMgr getKnowledgeMgrById(String knowledgeMgrId);

    List<KnowledgeMgrDto> queryKnowledgeMgrList();

    List<KnowledgeMgrDto> dimQueryKnowledgeMgr(KnowledgeMgrDto knowledgeMgrDto);
}