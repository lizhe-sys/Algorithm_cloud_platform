package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.LogRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogRecordDAO {

    void addLogRecord(LogRecord logRecord);

    List<LogRecord> getLogRecordList();

    List<LogRecord> dimQueryLogRecordByPage(LogRecord logRecord);

    void addFirstPageLogRecord(LogRecord logRecord);

    // 查询出首页显示日志信息，只查询出最新的20条记录
    List<LogRecord> logInfo();

}
