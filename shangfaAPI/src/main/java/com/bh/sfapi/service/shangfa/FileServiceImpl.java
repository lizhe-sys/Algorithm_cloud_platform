package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.dao.FileDAO;
import com.bh.sfapi.entity.shangfa.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService{


    @Autowired
    FileDAO fileDAO;


    @Override
    public List<Files> getFileListByDataMgrId(Long dataMgrId) {
        return fileDAO.getFileListByDataMgrId( dataMgrId );
    }
}
