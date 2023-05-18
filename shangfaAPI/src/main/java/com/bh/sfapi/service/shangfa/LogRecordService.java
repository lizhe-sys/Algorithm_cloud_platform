package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.entity.shangfa.LogRecord;

import java.util.List;

public interface LogRecordService {

    void addLogRecord(LogRecord logRecord);

    List<LogRecord> getLogRecordList();

    List<LogRecord> dimQueryLogRecordByPage( LogRecord logRecord);

    void addLog( Long userId , String moduleName , String operateDesc );
}
