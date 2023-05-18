package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.DataMgr;
import com.bh.sfapi.entity.shangfa.firstPage.DataMgrMapCount;
import com.bh.sfapi.entity.shangfa.firstPage.TypeCountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Mapper
public interface DataMgrDAO extends BaseDAO< DataMgr , String > {


    public void createDataTable(@Param("mysqlTable") String mysqlTable , @Param("fields" ) List<String> fields );

    public int checkTableIsExist( String mysqlTable );

    public int batchInsertData(@Param("mysqlTable") String mysqlTable, @Param("cols") Set<String> cols, @Param("data") List<Map<String, Object>> data);

    public List<DataMgr> queryDataMgrByMySqlTable( String mysqlTable );

    public DataMgr queryDataMgrById( Long dataMgrId );

    public DataMgr queryDataMgrByModelMgrId( Long modelMgrId );

    public void updateDataMgr( DataMgr dataMgr );

    public List<String> queryFileds( @Param("mysqlTable") String mysqlTable , @Param("mysqlDatabase") String mysqlDatabase );

    public List<DataMgr> queryDataMgrList();

    List<DataMgr> dimQueryDataMgr(DataMgr dataMgr);

    List<DataMgr> queryDataMgrUnFinishedList();

    DataMgr queryDataMgrByName(String dataMgrName);

    void addOtherTypeDataMgr(DataMgr dataMgr);

    List<DataMgr> queryDataTypeDataMgrList();

    List<TypeCountDto> getTypeCount();

    List<DataMgrMapCount> dataMgrMapCount();

    List<DataMgr> getDataMgrTimeList();

    List<String> getInfluxdbDataBase();

    List<String> getInfluxdbMeasurement();

    List<String> getInfluxdbMotorNo();

    List<DataMgr> dimQueryDataMgrTimeList(DataMgr dataMgr);

    void deleteTest2();

    List<DataMgr> queryDataMgrByuserId(Long userId);

    Long getid(Long userId);
}
