package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.DataMgrDAO;
import com.bh.sfapi.dao.ModelMgrDAO;
import com.bh.sfapi.entity.shangfa.DataMgr;
import com.bh.sfapi.entity.shangfa.ModelMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/12 14:12
 * @desc
 */

@Service
@Transactional
public class ModelMgrServiceImpl implements ModelMgrService {
    @Autowired
    ModelMgrDAO modelMgrDAO;


    @Override
    public void addModelMgr(ModelMgr modelMgr) {
        modelMgrDAO.save( modelMgr );
    }

    @Override
    public void updateModelMgr(ModelMgr modelMgr) {
        modelMgrDAO.update( modelMgr );
    }

    @Override
    public void deleteModelMgr(String modelId) {
        ModelMgr modelMgr= modelMgrDAO.getModelMgrById(modelId);
        if( modelMgr == null ) return;
        modelMgr.setDeleted( 1 );
        modelMgrDAO.update( modelMgr );
    }

    @Override
    public ModelMgr getModelMgrById(String modelId) {
        return modelMgrDAO.getModelMgrById( modelId );
    }

    @Override
    public List<ModelMgr> getModelMgrList() {
        return modelMgrDAO.findAll();
    }

    @Override
    public List<ModelMgr> dimQueryModelMgr(ModelMgr modelMgr) {
        return modelMgrDAO.dimQueryModelMgr( modelMgr );
    }

    @Override
    public List<ModelMgr> getModelMgrListByDataMgrId(Long dataMgrId , Long userId ) {
        return modelMgrDAO.getModelMgrListByDataMgrId( dataMgrId , userId );
    }

    @Override
    public ModelMgr getNewModelMgr(Long userId) {
        return modelMgrDAO.getNewModelMgr( userId );
    }

    @Override
    public ModelMgr getModelMgrByName(String modelName) {
        return modelMgrDAO.getModelMgrByName( modelName );
    }

    @Override
    public List<ModelMgr> getModelMgrListByStdModelId(Long stdModelId) {
        return modelMgrDAO.getModelMgrListByStdModelId( stdModelId );
    }

    @Override
    public List<ModelMgr> getSharedModelMgrList(Long userId) {
        return modelMgrDAO.getSharedModelMgrList( userId );
    }

}
