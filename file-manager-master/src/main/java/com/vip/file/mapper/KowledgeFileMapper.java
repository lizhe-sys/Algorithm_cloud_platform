package com.vip.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vip.file.model.dto.GetFileDto;
import com.vip.file.model.entity.Files;
import com.vip.file.model.entity.KownledgeFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KowledgeFileMapper extends BaseMapper<KownledgeFile> {
    /**
     * 获取文件列表
     *
     * @return
     */
    List<GetFileDto> selectFileList();

    /**
     * 判断文件是否已存在
     *
     * @param fileName
     * @return
     */
    boolean fileIsExist(@Param("fileName") String fileName);

    /**
     * 根据datamgrId查询文件列表
     *
     * @param datamgrId
     * @return
     */
    List<GetFileDto> getListByDataMgrId( Long datamgrId );

    /**
     * 根据fileId删除对应文件
     * @param fileId
     * @return
     */
    int deleteFile(String fileId);

    /**
     * 根据faultcaseId查询文件列表
     *
     * @param faultcaseId
     * @return
     */
    List<GetFileDto> getListByFaultcaseId(long faultcaseId);

    /**
     * 根据faultcaseId查询文件列表
     *
     * @param synthesisId
     * @return
     */
    List<GetFileDto> getListBySynthesisId(long synthesisId);

}

