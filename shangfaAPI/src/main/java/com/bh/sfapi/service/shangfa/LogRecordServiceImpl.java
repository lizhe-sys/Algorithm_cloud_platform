package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.dao.LogRecordDAO;
import com.bh.sfapi.entity.shangfa.LogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LogRecordServiceImpl implements LogRecordService{

    @Autowired
    LogRecordDAO logRecordDAO;

    @Override
    public void addLogRecord(LogRecord logRecord) {
        logRecord.setCreateTime( new Date() );
        // 增加日志记录
        logRecordDAO.addLogRecord( logRecord );

        // 增加首页日志记录（排除用户日志和docker日志）
        if( "user".equals( logRecord.getModuleName() ) || "docker".equals( logRecord.getModuleName()) ){
            return;
        }
        // 只需要新增和编辑的日志记录
        if( logRecord.getOperateDesc().startsWith("增加") || logRecord.getOperateDesc().startsWith("编辑" ) ){
            logRecordDAO.addFirstPageLogRecord( logRecord );
        }
    }

    @Override
    public void addLog( Long userId , String moduleName , String operateDesc ){
        LogRecord logRecord = new LogRecord();
        logRecord.setUserId( userId );
        logRecord.setModuleName( moduleName );
        logRecord.setOperateDesc( operateDesc );
        addLogRecord( logRecord );
    }

    @Override
    public List<LogRecord> getLogRecordList() {
        return logRecordDAO.getLogRecordList();
    }

    @Override
    public List<LogRecord> dimQueryLogRecordByPage( LogRecord logRecord ) {
        return logRecordDAO.dimQueryLogRecordByPage( logRecord );
    }
}
