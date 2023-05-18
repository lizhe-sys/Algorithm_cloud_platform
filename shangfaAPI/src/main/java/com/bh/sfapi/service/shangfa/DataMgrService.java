package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.DataMgr;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataMgrService {

    public void createDataTable(String mysqlTable ,List<String> fields );

    public int checkTableIsExist( String mysqlTable );

    public Long save( DataMgr dataMgr );

    public List<DataMgr> queryDataMgrByMySqlTable( String mysqlTable );

    public DataMgr queryDataMgrById( Long dataMgrId );

    public DataMgr queryDataMgrByModelMgrId( Long modelMgrId );

    public int batchInsertData( String mysqlTable, Set<String> cols, List<Map<String, Object>> data);

    void updateDataMgr( DataMgr dataMgr  ) ;

    public List<String> queryFileds( String mysqlTable ,  String mysqlDatabase );

    public List<DataMgr> queryDataMgrList();

    // 模糊查询
    List<DataMgr> dimQueryDataMgr(DataMgr dataMgr);

    List<DataMgr> queryDataMgrUnFinishedList();

    DataMgr queryDataMgrByName(String dataMgrName);

    void addOtherTypeDataMgr(DataMgr dataMgr);

    List<DataMgr> queryDataTypeDataMgrList();

    void deleteTest2();


    List<DataMgr> queryDataMgrByuserId(Long userId);
}
