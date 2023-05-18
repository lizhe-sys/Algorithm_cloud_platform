package com.vip.file.service;

import cn.novelweb.tool.upload.local.pojo.UploadFileParam;
import com.vip.file.model.dto.AddFileDto;
import com.vip.file.model.dto.GetFileDto;
import com.vip.file.model.dto.GetLogFileDto;
import com.vip.file.model.entity.Files;
import com.vip.file.model.response.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 文件上传下载 服务类
 * </p>
 *
 * @author LEON
 * @since 2020-05-29
 */
public interface IFileService {
    /**
     * 小文件上传
     *
     * @param file
     * @return
     */
    Result<String> uploadConfigFiles(MultipartFile file);

    /**
     * 小文件上传
     *
     * @param file
     * @return
     */
    Result<String> uploadFiles(MultipartFile file);

    /**
     * 保存字段文件信息
     * @param fileName
     * @param suffix
     * @return
     */
    Result<String> uploadConfigFiles(String fileName , String suffix );

    /**
     * 获取文件输入流
     *
     * @param id
     * @return
     */
    InputStream getFileInputStream(String id);

    /**
     * 获取指定文件详情
     *
     * @param id
     * @return
     */
    Result<Files> getFileDetails(String id);

    /**
     * 分页获取文件信息
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    Result<List<GetFileDto>> getFileList(Integer pageNo, Integer pageSize);

    /**
     * 检查文件MD5
     *
     * @param md5
     * @param fileName
     * @return
     */
    Result<Object> checkFileMd5(String md5, String fileName);

    /**
     * 断点续传
     *
     * @param param
     * @param request
     * @return
     */
    Result<Object> breakpointResumeUpload(UploadFileParam param, HttpServletRequest request);

    /**
     * 添加文件
     *
     * @param dto
     * @return
     */
    Result<String> addFile(AddFileDto dto);

    /**
     * 根据dataMgrId查询文件列表
     * @param dataMgrId
     * @return
     */
    Result<List<GetFileDto>> getListByDataMgrId(Long dataMgrId);

    /**
     * 根据文件id删除文件
     * @param fileId
     * @return
     */
    Result<Boolean> deleteFile(String fileId);

    /**
     * 音视频数据流返回
     */

    void videoData( String fileId, HttpServletRequest request, HttpServletResponse response );

}
