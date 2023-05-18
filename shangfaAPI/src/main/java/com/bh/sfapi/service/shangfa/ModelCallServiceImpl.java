package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.ModelCallDAO;
import com.bh.sfapi.entity.shangfa.ModelCall;
import com.bh.sfapi.entity.shangfa.dto.ModelCallInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/12 14:12
 * @desc
 */

@Service
@Transactional
public class ModelCallServiceImpl implements ModelCallService {

    @Autowired
    ModelCallDAO modelCallDAO;

    @Override
    public void addModelCall(ModelCall modelCall) {
        modelCall.setIsTrue( 1 );
        modelCall.setCallTime( new Date() );
        modelCall.setDeleted( 0 );
        modelCallDAO.save( modelCall );
    }

    @Override
    public List<ModelCallInfoDto> modelCallInfo() {
        return modelCallDAO.modelCallInfo();
    }

    @Override
    public List<ModelCall> getModelCallByStdModelId(Long stdModelId) {
        return modelCallDAO.getModelCallByStdModelId( stdModelId );
    }

    @Override
    public Integer getCallCountByStdModelId(Long stdModelId) {
        return modelCallDAO.getCallCountByStdModelId( stdModelId );
    }

    @Override
    public void deleteModelCallByStdModelId(Long stdModelId) {
        modelCallDAO.deleteModelCallByStdModelId( stdModelId );
    }

    @Override
    public void deleteModelCallByModelMgrId(Long modelMgrId) {
        modelCallDAO.deleteModelCallByModelMgrId( modelMgrId );
    }


}
