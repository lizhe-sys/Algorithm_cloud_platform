package com.vip.file.controller;

import cn.novelweb.tool.upload.local.pojo.UploadFileParam;
import com.vip.file.config.SystemException;
import com.vip.file.constant.SysConstant;
import com.vip.file.model.dto.AddFileDto;
import com.vip.file.model.dto.GetFileDto;
import com.vip.file.model.entity.Files;
import com.vip.file.model.entity.KownledgeFile;
import com.vip.file.model.response.*;
import com.vip.file.service.IFileService;
import com.vip.file.service.KownledgeFileService;
import com.vip.file.utils.EmptyUtils;
import com.vip.file.utils.EncodingUtils;
import com.vip.file.utils.InputStreamUtils;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文件上传下载 前端控制器
 * </p>
 *
 * @author LEON
 * @since 2020-05-29
 */
@Slf4j
@RestController
@RequestMapping("/kownledgeFile")
@RequiredArgsConstructor
public class KownledgeFileController {

    private final KownledgeFileService fileService;

    /**
     * 文件列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public RestResponse<List<GetFileDto>> getFileList(@RequestParam Integer pageNo, @RequestParam Integer pageSize) throws IOException {
        return RestResponses.newResponseFromResult(fileService.getFileList(pageNo, pageSize));
    }

    /**
     * 通过id查询文件信息
//     */
    @GetMapping(value = "/getFileInfo")
    public RestResponse<KownledgeFile> getFileInfo(String id ) throws IOException {
        return RestResponses.newResponseFromResult(fileService.getFileDetails( id ));
    }

    /**
     * 根据知识类型查询文件列表
     * @param
     * @return
     * @throws IOException
     */
    @GetMapping( value = "/getListByKownledge")
    public RestResponse<List<GetFileDto>> getListByKownledge( HttpServletRequest request ) throws IOException {

        Cookie[] cookies = request.getCookies();
        int kownledgeType = -1;
        long faultcaseId = 0 , synthesisId = 0;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if( "kownledgeType".equals( cookie.getName() ) ){
                    kownledgeType = Integer.parseInt( cookie.getValue() );
                    break;
                }
            }
//            System.out.println( kownledgeType );
            if( kownledgeType == 0 ){
                for (Cookie cookie : cookies) {
                    if( "faultcaseId".equals( cookie.getName() ) ){
                        faultcaseId = Long.parseLong( cookie.getValue() );
                        return RestResponses.newResponseFromResult(fileService.getListByFaultcaseId( faultcaseId ));

                    }
                }
            }
            if( kownledgeType == 1 ){
                for (Cookie cookie : cookies) {
                    if( "synthesisId".equals( cookie.getName() ) ){
                        synthesisId = Long.parseLong( cookie.getValue() );
                        return RestResponses.newResponseFromResult(fileService.getListBySynthesisId( synthesisId ));

                    }
                }
            }
        }
//        return RestResponses.newResponseFromResult(fileService.getListByFaultcaseId( 1 ));
        List<GetFileDto> res = new ArrayList<>();
        return RestResponses.newResponseFromResult( Results.newSuccessResult( res , "查询成功", res.size()) );
    }

    /**
     * 根据文件id删除文件
     * @param fileId
     * @return
     * @throws IOException
     */
    @GetMapping( value = "/deleteFile")
    public RestResponse deleteFile( String fileId ) throws IOException {
        try {
            fileService.deleteFile( fileId );
            return RestResponses.newSuccessResponse( true );
        }
        catch ( Exception e ){
            log.info( e.getMessage() );
        }
        return RestResponses.newSuccessResponse( false );
    }

    /**
     * 普通上传方式上传文件：用于小文件的上传，等待时间短，不会产生配置数据
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public RestResponse<String> uploadFiles( MultipartFile file  ) {
        if (file.isEmpty()) {
            return RestResponses.newFailResponse(ErrorCode.INVALID_PARAMETER, "文件不能为空");
        }
        return RestResponses.newResponseFromResult(fileService.uploadFiles(file));
    }

    /**
     * 添加文件
     * 断点续传完成后上传文件信息进行入库操作
     *
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public RestResponse<String> addFile(@RequestBody AddFileDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RestResponses.newFailResponse(ErrorCode.INVALID_PARAMETER, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
//        System.out.println( dto );
        return RestResponses.newResponseFromResult(fileService.addFile(dto));
    }

    /**
     * 检查文件MD5（文件MD5若已存在进行秒传）
     *
     * @param md5
     * @param fileName
     * @return
     */
    @GetMapping(value = "/check-file")
    public RestResponse<Object> checkFileMd5(String md5, String fileName) {
        return RestResponses.newResponseFromResult(fileService.checkFileMd5(md5, fileName));
    }

    /**
     * 断点续传方式上传文件：用于大文件上传
     *
     * @param param
     * @param request
     * @return
     */
    @PostMapping(value = "/breakpoint-upload", consumes = "multipart/*", headers = "content-type=multipart/form-data", produces = "application/json;charset=UTF-8")
    public RestResponse<Object> breakpointResumeUpload(UploadFileParam param, HttpServletRequest request) {
        return RestResponses.newResponseFromResult(fileService.breakpointResumeUpload(param, request));
    }

    /**
     * 图片/PDF查看
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/view/{fileId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> viewFilesImage(@PathVariable String id) throws IOException {
        Result<KownledgeFile> fileDetails = fileService.getFileDetails(id);
        if (fileDetails.isSuccess()) {
            if (!EmptyUtils.basicIsEmpty(fileDetails.getData().getSuffix()) && !SysConstant.IMAGE_TYPE.contains(fileDetails.getData().getSuffix())) {
                throw new SystemException(ErrorCode.FILE_ERROR.getCode(), "非图片/PDF类型请先下载");
            }
        } else {
            throw new SystemException(fileDetails.getErrorCode().getCode(), fileDetails.getDescription());
        }
        InputStream inputStream = fileService.getFileInputStream(id);
        return new ResponseEntity<>(InputStreamUtils.inputStreamToByte(inputStream), HttpStatus.OK);
    }


    /**
     *
     * @param fileId
     * @param userId
     * @param isSource
     * @param request
     * @param response
     */
    @GetMapping(value = "/download")
    public void viewFilesImage( String fileId, String userId,@RequestParam(required = false) Boolean isSource, HttpServletRequest request, HttpServletResponse response) {

        log.info("用户：{}，下载文件：{}，时间：{}" , userId , fileId , new Date() );
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            Result<KownledgeFile> fileDetails = fileService.getFileDetails(fileId);
            if (!fileDetails.isSuccess()) {
                throw new SystemException(fileDetails.getErrorCode().getCode(), fileDetails.getDescription());
            }
            String filename = (!EmptyUtils.basicIsEmpty(isSource) && isSource) ? fileDetails.getData().getFileName() : fileDetails.getData().getFilePath();
            inputStream = fileService.getFileInputStream(fileId);
            response.setHeader("Content-Disposition", "attachment;filename=" + EncodingUtils.convertToFileName(request, filename));
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            // 获取输出流
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("文件下载出错", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

