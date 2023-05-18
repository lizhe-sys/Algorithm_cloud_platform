package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.entity.shangfa.LogRecord;
import com.bh.sfapi.entity.shangfa.firstPage.MotorInfo;
import com.bh.sfapi.entity.shangfa.firstPage.*;

import java.util.List;
import java.util.Map;

public interface FirstPageService {

    FirstPage firstPageInfo();

    void updatefirstPage(FirstPage firstPage);

    void updatefirstPageByType(int type);

    Map firstPageStdModelInfo(int year );

    List<ModelMgrInfo> modelMgrInfo();

    List<DataMgrMapCount> dataMgrMapCount();

    List<FaultCaseRecordCount> faultCaseRecordCount();

    List<LogRecord> logInfo();

    DataMgrInfo firstPageDataMgrInfo(MotorInfo motorInfo);

    Map<String, List> firstPageMotorInfo();

}
