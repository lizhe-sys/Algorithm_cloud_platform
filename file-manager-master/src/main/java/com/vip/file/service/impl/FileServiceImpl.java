package com.vip.file.service.impl;

import cn.novelweb.tool.upload.local.LocalUpload;
import cn.novelweb.tool.upload.local.pojo.UploadFileParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vip.file.constant.SysConstant;
import com.vip.file.model.dto.AddFileDto;
import com.vip.file.model.dto.GetFileDto;
import com.vip.file.model.dto.GetLogFileDto;
import com.vip.file.model.entity.Files;
import com.vip.file.mapper.FilesMapper;
import com.vip.file.model.response.ErrorCode;
import com.vip.file.model.response.Result;
import com.vip.file.model.response.Results;
import com.vip.file.service.IFileService;
import com.vip.file.utils.*;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文件上传下载 服务实现类
 * </p>
 *
 * @author LEON
 * @since 2020-05-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements IFileService {

    private final FilesMapper filesMapper;
    @Value("${file.save-path:/data-center/files/vip-file-manager}")
    private String savePath;
    @Value("${file.conf-path:/data-center/files/vip-file-manager/conf}")
    private String confFilePath;
    @Value("${file.fieldLimitCount}")
    private Integer fieldLimitCount;
    @Value("${file.logfile-path}")
    private String logfilePath;
    @Value("${file.logLimitCount}")
    private Integer logLimitCount;


    @Override
    public Result<List<GetFileDto>> getFileList(Integer pageNo, Integer pageSize) {
        try {
            PageHelper.startPage(pageNo, pageSize);
            List<GetFileDto> result = filesMapper.selectFileList();
            PageInfo<GetFileDto> pageInfo = new PageInfo<>(result);
            return Results.newSuccessResult(pageInfo.getList(), "查询成功", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取文件列表出错", e);
        }
        return Results.newFailResult(ErrorCode.DB_ERROR, "查询失败");
    }

    @Override
    public Result<String> uploadConfigFiles(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            // 文件名非空校验
            if (EmptyUtils.basicIsEmpty(fileName)) {
                return Results.newFailResult(ErrorCode.FILE_ERROR, "文件名不能为空");
            }
            // 大文件判定
            if (file.getSize() > SysConstant.MAX_SIZE) {
                return Results.newFailResult(ErrorCode.FILE_ERROR, "文件过大，请使用大文件传输");
            }
            // 生成新文件名
            String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;

            // 临时文件（此处的功能是检查上传的配置文件字段是否超过限制的字段数）
            MultipartFile tmp = file;
            String newName = UuidUtils.uuid() + suffixName;
            // 重命名文件
            File newFile = new File(savePath, newName);
            // 如果该存储路径不存在则新建存储路径
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            // 临时文件写入
            tmp.transferTo(newFile);
            // 保存文件信息
            Files files = new Files().setFilePath(newName).setFileName(fileName).setSuffix(suffixName == null ? null : suffixName.substring(1));
            filesMapper.insert(files);
            return Results.newSuccessResult(files.getId(), "上传完成");
        } catch (Exception e) {
            log.error("上传协议文件出错", e);
        }
        return Results.newFailResult(ErrorCode.FILE_ERROR, "上传失败");
    }

    // 通过上传的字段列表已经保存为文件，直接保存mysql记录信息即可
    @Override
    public Result<String> uploadConfigFiles(String fileName, String suffix) {
        try {

            String newName = UuidUtils.uuid() + suffix;
            File tmpFile = new File(savePath, fileName);
            File newFile = new File(savePath, newName);
            tmpFile.renameTo( newFile );
            Files files = new Files().setFilePath(newName).setFileName(fileName).setSuffix(suffix == null ? null : suffix.substring(1));
            filesMapper.insert(files);
            return Results.newSuccessResult(files.getId(), "上传完成");

        }
        catch (Exception e) {
            log.error("上传协议文件出错", e);
        }
        return Results.newFailResult(ErrorCode.FILE_ERROR, "上传失败");
    }



    @Override
    public Result<String> uploadFiles(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            // 文件名非空校验
            if (EmptyUtils.basicIsEmpty(fileName)) {
                return Results.newFailResult(ErrorCode.FILE_ERROR, "文件名不能为空");
            }
            // 大文件判定
            if (file.getSize() > SysConstant.MAX_SIZE) {
                return Results.newFailResult(ErrorCode.FILE_ERROR, "文件过大，请使用大文件传输");
            }
            // 生成新文件名
            String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;

            // 临时文件（此处的功能是检查上传的配置文件字段是否超过限制的字段数）
            MultipartFile tmp = file;
            String tmpName = "tmp" + suffixName;
            // 重命名文件
            File tmpFile = new File(savePath, tmpName );
            // 如果该存储路径不存在则新建存储路径
            if (!tmpFile.getParentFile().exists()) {
                tmpFile.getParentFile().mkdirs();
            }
            // 临时文件写入
            tmp.transferTo(tmpFile);
            String newName = UuidUtils.uuid() + suffixName;
            // 重命名文件
            File newFile = new File(savePath, newName);
            // 如果该存储路径不存在则新建存储路径
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            // 临时文件重命名为生成的文件
            tmpFile.renameTo( newFile );
            // 保存文件信息
            Files files = new Files().setFilePath(newName).setFileName(fileName).setSuffix(suffixName == null ? null : suffixName.substring(1));
            filesMapper.insert(files);
            return Results.newSuccessResult(files.getId(), "上传完成");
        } catch (Exception e) {
            log.error("上传协议文件出错", e);
        }
        return Results.newFailResult(ErrorCode.FILE_ERROR, "上传失败");
    }



    @Override
    public Result<Object> checkFileMd5(String md5, String fileName) {
        try {
            cn.novelweb.tool.http.Result result = LocalUpload.checkFileMd5(md5, fileName, confFilePath, savePath);
            return NovelWebUtils.forReturn(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Results.newFailResult(ErrorCode.FILE_UPLOAD, "上传失败");
    }

    @Override
    public Result breakpointResumeUpload(UploadFileParam param, HttpServletRequest request) {
        try {
            // 这里的 chunkSize(分片大小) 要与前端传过来的大小一致
            cn.novelweb.tool.http.Result result = LocalUpload.fragmentFileUploader(param, confFilePath, savePath, 5242880L, request);
            return NovelWebUtils.forReturn(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Results.newFailResult(ErrorCode.FILE_UPLOAD, "上传失败");
    }

    @Override
    public Result<String> addFile(AddFileDto dto) {
        try {
            Files file = new Files();
            BeanUtils.copyProperties(dto, file);

            String newName = UuidUtils.uuid() + "." +dto.getSuffix();
            // 重命名文件
            File tmpFile = new File(savePath, dto.getFileName() );
            File newFile = new File(savePath, newName );
            tmpFile.renameTo( newFile );

            if (filesMapper.insert(file.setFilePath( newName ).setDatamgrId(dto.getDatamgrId())) == 1) {
                return Results.newSuccessResult(null, "添加成功");
            }
        } catch (Exception e) {
            log.error("添加文件出错", e);
        }
        return Results.newFailResult(ErrorCode.FILE_UPLOAD, "添加失败");
    }

    @Override
    public Result<List<GetFileDto>> getListByDataMgrId(Long dataMgrId) {
        try {
            List<GetFileDto> result = filesMapper.getListByDataMgrId( dataMgrId );
            return Results.newSuccessResult( result , "查询成功", result.size());
        } catch (Exception e) {
            log.error("获取文件列表出错", e);
        }
        return Results.newFailResult(ErrorCode.DB_ERROR, "查询失败");
    }

    @Override
    public Result<Boolean> deleteFile(String fileId) {
        try {
            filesMapper.deleteFile(fileId);
            return Results.newSuccessResult( true , "删除成功" );
        } catch (Exception e) {
            log.error("删除文件出错", e);
        }
        return Results.newFailResult(ErrorCode.DB_ERROR, "删除失败");
    }

    @Override
    public void videoData( String fileId, HttpServletRequest request, HttpServletResponse response ) {


    }

    @Override
    public InputStream getFileInputStream(String id) {
        try {
            Files files = filesMapper.selectById(id);
            File file = new File(savePath + File.separator + files.getFilePath());
            return new FileInputStream(file);
        } catch (Exception e) {
            log.error("获取文件输入流出错", e);
        }
        return null;
    }

    @Override
    public Result<Files> getFileDetails(String id) {
        try {
            Files files = filesMapper.selectById(id);
            return Results.newSuccessResult(files, "查询成功");
        } catch (Exception e) {
            log.error("获取文件详情出错", e);
        }
        return Results.newFailResult(ErrorCode.DB_ERROR, "查询失败");
    }
}
