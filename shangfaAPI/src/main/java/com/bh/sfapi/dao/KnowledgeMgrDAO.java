package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.HealthMgr;
import com.bh.sfapi.entity.shangfa.KnowledgeMgr;
import com.bh.sfapi.entity.shangfa.dto.HealthMgrDto;
import com.bh.sfapi.entity.shangfa.dto.KnowledgeMgrDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface KnowledgeMgrDAO extends BaseDAO<KnowledgeMgr, String > {

    KnowledgeMgr getKnowledgeMgrById(String knowledgeMgrId);

    List<KnowledgeMgrDto> queryKnowledgeMgrList();

    List<KnowledgeMgrDto> dimQueryKnowledgeMgr(KnowledgeMgrDto knowledgeMgrDto);

}
