package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.ModelCall;
import com.bh.sfapi.entity.shangfa.dto.ModelCallInfoDto;

import java.util.List;

public interface ModelCallService {

    void addModelCall(ModelCall modelCall);

    List<ModelCallInfoDto> modelCallInfo();

    List<ModelCall> getModelCallByStdModelId(Long stdModelId);

    Integer getCallCountByStdModelId(Long stdModelId);

    void deleteModelCallByStdModelId(Long stdModelId);

    void deleteModelCallByModelMgrId(Long modelMgrId);

}
