package com.bh.sfapi.dao;


import com.bh.sfapi.entity.shangfa.FaultCase;
import com.bh.sfapi.entity.shangfa.dto.FaultCaseDto;
import com.bh.sfapi.entity.shangfa.firstPage.FaultCaseRecordCount;
import com.bh.sfapi.entity.shangfa.firstPage.TypeCountDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaultCaseDAO extends BaseDAO<FaultCase , String > {

    FaultCaseDto queryFaultCaseById(long faultCaseId);

    void deleteFaultCaseById(long faultCaseId);

    List<FaultCaseDto> queryFaultCaseList();

    List<FaultCaseDto> dimQueryFaultCaseListByPage(FaultCaseDto faultCase);

    void updateFaultCase(FaultCase faultCase);

    List<FaultCase> queryFaultCaseListByMapMgrId(long mapMgrId);

    List<FaultCaseRecordCount> faultCaseRecordCount();

    List<TypeCountDto> getTypeCount();

    void batchInsert(List<FaultCase> list);

    FaultCase faultCaseByMapMgrId(Long mapMgrID);
}
