package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.entity.shangfa.Files;

import java.util.List;

public interface FileService {

    List<Files> getFileListByDataMgrId(Long dataMgrId);
}
