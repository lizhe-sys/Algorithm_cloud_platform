package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.Files;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/14 21:28
 * @desc
 */
@Mapper
public interface FileDAO extends BaseDAO<Files , String> {

    List<Files> getFileListByDataMgrId(Long dataMgrId);
}
