package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.DataMgrDAO;
import com.bh.sfapi.entity.shangfa.DataMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
public class DataMgrServiceImpl implements DataMgrService {

    @Autowired
    DataMgrDAO dataMgrDAO;

    @Override
    public void createDataTable( String mysqlTable, List<String> fields) {
        dataMgrDAO.createDataTable( mysqlTable , fields );
    }

    @Override
    public int checkTableIsExist(String mysqlTable) {
        return dataMgrDAO.checkTableIsExist( mysqlTable );
    }

    @Override
    public Long save(DataMgr dataMgr) {
        dataMgrDAO.save( dataMgr );
        return dataMgrDAO.getid(dataMgr.getUserId());
    }

    @Override
    public List<DataMgr> queryDataMgrByMySqlTable(String mysqlTable) {
        return dataMgrDAO.queryDataMgrByMySqlTable( mysqlTable );
    }

    @Override
    public DataMgr queryDataMgrById( Long dataMgrId) {
        return dataMgrDAO.queryDataMgrById( dataMgrId );
    }

    @Override
    public DataMgr queryDataMgrByModelMgrId(Long modelMgrId) {
        return dataMgrDAO.queryDataMgrByModelMgrId( modelMgrId );
    }

    @Override
    public int batchInsertData(String mysqlTable, Set<String> cols, List<Map<String, Object>> data) {
        return dataMgrDAO.batchInsertData( mysqlTable , cols , data );
    }

    @Override
    public void updateDataMgr( DataMgr dataMgr ) {
        dataMgrDAO.updateDataMgr( dataMgr );
    }

    @Override
    public List<String> queryFileds(String mysqlTable, String mysqlDatabase) {
        return dataMgrDAO.queryFileds( mysqlTable , mysqlDatabase );
    }

    @Override
    public List<DataMgr> queryDataMgrList() {
        return dataMgrDAO.queryDataMgrList();
    }

    @Override
    public List<DataMgr> dimQueryDataMgr(DataMgr dataMgr) {
        return dataMgrDAO.dimQueryDataMgr( dataMgr );
    }

    @Override
    public List<DataMgr> queryDataMgrUnFinishedList() {
        return dataMgrDAO.queryDataMgrUnFinishedList();
    }

    @Override
    public DataMgr queryDataMgrByName(String dataMgrName) {
        return dataMgrDAO.queryDataMgrByName( dataMgrName );
    }

    @Override
    public void addOtherTypeDataMgr(DataMgr dataMgr) {
        dataMgr.setFinished( 1 );
        dataMgr.setDeleted( 0 );
        dataMgr.setCreateTime( new Date() );

        dataMgrDAO.addOtherTypeDataMgr( dataMgr );
    }

    @Override
    public List<DataMgr> queryDataTypeDataMgrList() {
        return dataMgrDAO.queryDataTypeDataMgrList();
    }

    @Override
    public void deleteTest2() {
        dataMgrDAO.deleteTest2();
    }

    @Override
    public List<DataMgr> queryDataMgrByuserId(Long userId) {
        return dataMgrDAO.queryDataMgrByuserId(userId);
    }


}
