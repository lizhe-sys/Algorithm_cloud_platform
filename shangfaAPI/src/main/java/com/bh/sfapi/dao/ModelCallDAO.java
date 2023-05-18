package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.ModelCall;
import com.bh.sfapi.entity.shangfa.ModelMgr;
import com.bh.sfapi.entity.shangfa.dto.ModelCallInfoDto;
import com.bh.sfapi.entity.shangfa.firstPage.StdModelInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ModelCallDAO extends BaseDAO< ModelCall, String > {

    List<ModelCallInfoDto> modelCallInfo();

    List<ModelCall> getModelCallByStdModelId(Long stdModelId);

    Integer getCallCountByStdModelId(Long stdModelId);

    void deleteModelCallByStdModelId(Long stdModelId);

    void deleteModelCallByModelMgrId(Long modelMgrId);

    List<StdModelInfo> firstPageModelCallInfo(int year);
}
