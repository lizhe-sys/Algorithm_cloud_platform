package com.bh.sfapi.dao;

import com.bh.sfapi.entity.RealTime;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RealTimeDAO extends BaseDAO<RealTime , String > {

    public void sava( RealTime realTime );

    RealTime queryRealTimeRecord(String influxdb_database);
}
