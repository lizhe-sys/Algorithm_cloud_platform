package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.FaultCase;
import com.bh.sfapi.entity.shangfa.dto.FaultCaseDto;

import java.util.List;

public interface FaultCaseService {

    void save(FaultCase faultCase);

    void batchInsert(List<FaultCase> list);

    FaultCaseDto queryFaultCaseById(long faultCaseId);

    void deleteFaultCaseById(long faultCaseId);

    List<FaultCaseDto> queryFaultCaseList();

    List<FaultCaseDto> dimQueryFaultCaseListByPage(FaultCaseDto faultCase);

    void updateFaultCase(FaultCase faultCase);

    List<FaultCase> queryFaultCaseListByMapMgrId(long mapMgrId);

    FaultCase faultCaseByMapMgrId(Long mapMgrID);
}
