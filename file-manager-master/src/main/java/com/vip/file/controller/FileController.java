package com.vip.file.controller;

import cn.novelweb.tool.upload.local.pojo.UploadFileParam;
import com.vip.file.config.SystemException;
import com.vip.file.constant.SysConstant;
import com.vip.file.constant.UrlConstant;
import com.vip.file.model.dto.AddFileDto;
import com.vip.file.model.dto.FieldCofig;
import com.vip.file.model.dto.GetFileDto;
import com.vip.file.model.dto.GetLogFileDto;
import com.vip.file.model.entity.Files;
import com.vip.file.model.response.ErrorCode;
import com.vip.file.model.response.RestResponse;
import com.vip.file.model.response.RestResponses;
import com.vip.file.model.response.Result;
import com.vip.file.service.IFileService;
import com.vip.file.utils.EmptyUtils;
import com.vip.file.utils.EncodingUtils;
import com.vip.file.utils.ExcelUtil;
import com.vip.file.utils.InputStreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;

    @Value("${file.logfile-path}")
    private String logfilePath;
    @Value("${file.save-path}")
    private String savePath;


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
    public RestResponse< Files > getFileList( String id ) throws IOException {
        return RestResponses.newResponseFromResult(fileService.getFileDetails( id ));
    }

    /**
     * 根据dataMgrId查询文件列表
     * @param
     * @return
     * @throws IOException
     */
    @GetMapping( value = "/getListByDataMgrId")
    public RestResponse<List<GetFileDto>> getListByDataMgrId( HttpServletRequest request ) throws IOException {

        Cookie[] cookies = request.getCookies();
        long dataMgrId = 0;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if( "dataMgrId".equals( cookie.getName() ) ){
                    dataMgrId = Long.parseLong( cookie.getValue() );
                    break;
                }
            }
        }
        return RestResponses.newResponseFromResult(fileService.getListByDataMgrId( dataMgrId ));
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
     * 普通上传方式上传文件：用于小文件的上传，等待时间短，不会产生配置数据
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadConfigFiles")
    public RestResponse<String> uploadConfigFiles( MultipartFile file  ) {
        if (file.isEmpty()) {
            return RestResponses.newFailResponse(ErrorCode.INVALID_PARAMETER, "文件不能为空");
        }
        return RestResponses.newResponseFromResult(fileService.uploadConfigFiles( file ) );
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
    @GetMapping(value = "/view/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> viewFilesImage(@PathVariable String id) throws IOException {
        Result<Files> fileDetails = fileService.getFileDetails(id);
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
            Result<Files> fileDetails = fileService.getFileDetails(fileId);
            if (!fileDetails.isSuccess()) {
                throw new SystemException(fileDetails.getErrorCode().getCode(), fileDetails.getDescription());
            }
            String filename = (!EmptyUtils.basicIsEmpty(isSource) && isSource) ? fileDetails.getData().getFileName() : fileDetails.getData().getFileName();
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

    /**
     * 上传字段生成文件然后进行保存
     * @return
     */
    @PostMapping(value = "/uploadFields")
    public RestResponse<String> uploadFields(@RequestBody FieldCofig fieldCofig) throws IOException {

        List< List<String> > lists = new ArrayList<>();

        if( fieldCofig.getFieldLsit() != null ){
            List<String> fieldLsit = fieldCofig.getFieldLsit();
            for( int i = 0; i < fieldLsit.size(); i++ ){
                List<String> fields = new ArrayList<>();
                fields.add( fieldLsit.get( i ) );
                lists.add( fields );
            }
            String[] key = { "Name" };

            long time = (new Date()).getTime();
            String fileName = time + ".xls";
            String suffix = ".xls";
            String filePath = savePath + "/" + fileName;
//            String filePath = savePath + "\\" + fileName;
            try {
                ExcelUtil.write( lists , key , filePath );
            } catch (Exception e) {
                return RestResponses.newFailResponse(ErrorCode.INVALID_PARAMETER, "写入出错");
            }
            return RestResponses.newResponseFromResult( fileService.uploadConfigFiles( fileName , suffix ) );
        }
        return RestResponses.newFailResponse(ErrorCode.INVALID_PARAMETER, "上传出错");
    }

//    /**
//     * 下载日志文件
//     *
//     * @param fileName
//     * @param request
//     * @param response
//     */
//    @GetMapping(value = "/downloadLog")
//    public void downloadLog( String fileName,  HttpServletRequest request, HttpServletResponse response) {
//        OutputStream outputStream = null;
//        InputStream inputStream = null;
//
//        String filePath = logfilePath + fileName;
////        log.info("下载日志文件：{}", filePath );
//
//        try {
//            File file = new File( filePath );
//            inputStream = new FileInputStream(file);
//            response.setHeader("Content-Disposition", "attachment;filename=" + EncodingUtils.convertToFileName(request, fileName));
//            response.setContentType("application/octet-stream");
//            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//            // 获取输出流
//            outputStream = response.getOutputStream();
//            IOUtils.copy(inputStream, outputStream);
//        } catch (IOException e) {
//            log.error("文件下载出错", e);
//        } finally {
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    //path为本地文件路径
    @GetMapping("videoPlay")
    public void play( String fileId , HttpServletRequest request, HttpServletResponse response) {
        RandomAccessFile targetFile = null;
        OutputStream outputStream = null;
        try {
            Result<Files> fileDetails = fileService.getFileDetails(fileId);
            if (!fileDetails.isSuccess()) {
                throw new SystemException(fileDetails.getErrorCode().getCode(), fileDetails.getDescription());
            }

            Files files = fileDetails.getData();
            String suffix = files.getSuffix();
            String path = savePath + File.separator + files.getFilePath();

            System.out.println( path );

            outputStream = response.getOutputStream();
            response.reset();
            //获取请求头中Range的值
            String rangeString = request.getHeader(org.springframework.http.HttpHeaders.RANGE);
            //打开文件
            File file = new File(path);
            if (file.exists()) {
                //使用RandomAccessFile读取文件
                targetFile = new RandomAccessFile(file, "r");
                long fileLength = targetFile.length();
                long requestSize = (int) fileLength;
                //分段下载视频
                if (StringUtils.hasText(rangeString)) {
                    //从Range中提取需要获取数据的开始和结束位置
                    long requestStart = 0, requestEnd = 0;
                    String[] ranges = rangeString.split("=");
                    if (ranges.length > 1) {
                        String[] rangeDatas = ranges[1].split("-");
                        requestStart = Integer.parseInt(rangeDatas[0]);
                        if (rangeDatas.length > 1) {
                            requestEnd = Integer.parseInt(rangeDatas[1]);
                        }
                    }
                    if (requestEnd != 0 && requestEnd > requestStart) {
                        requestSize = requestEnd - requestStart + 1;
                    }
                    //根据协议设置请求头
                    response.setHeader(org.springframework.http.HttpHeaders.ACCEPT_RANGES, "bytes");
                    if ( "mp3".equals( suffix ) )
                        response.setHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "video/mp3");
                    if ( "mp4".equals( suffix ) )
                        response.setHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "video/mp4");
                    if (!StringUtils.hasText(rangeString)) {
                        response.setHeader(org.springframework.http.HttpHeaders.CONTENT_LENGTH, fileLength + "");
                    } else {
                        long length;
                        if (requestEnd > 0) {
                            length = requestEnd - requestStart + 1;
                            response.setHeader(org.springframework.http.HttpHeaders.CONTENT_LENGTH, "" + length);
                            response.setHeader(org.springframework.http.HttpHeaders.CONTENT_RANGE, "bytes " + requestStart + "-" + requestEnd + "/" + fileLength);
                        } else {
                            length = fileLength - requestStart;
                            response.setHeader(org.springframework.http.HttpHeaders.CONTENT_LENGTH, "" + length);
                            response.setHeader(org.springframework.http.HttpHeaders.CONTENT_RANGE, "bytes " + requestStart + "-" + (fileLength - 1) + "/"
                                    + fileLength);
                        }
                    }
                    //断点传输下载视频返回206
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    //设置targetFile，从自定义位置开始读取数据
                    targetFile.seek(requestStart);
                } else {
                    //如果Range为空则下载整个视频
                    if ( "mp3".equals( suffix ) )
                        response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.mp3");
                    if ( "mp4".equals( suffix ) )
                        response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.mp4");
                    //设置文件长度
                    response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength));
                }

                //从磁盘读取数据流返回
                byte[] cache = new byte[4096];
                try {
                    while (requestSize > 0) {
                        int len = targetFile.read(cache);
                        if (requestSize < cache.length) {
                            outputStream.write(cache, 0, (int) requestSize);
                        } else {
                            outputStream.write(cache, 0, len);
                            if (len < cache.length) {
                                break;
                            }
                        }
                        requestSize -= cache.length;
                    }
                } catch (IOException e) {
                    // tomcat原话。写操作IO异常几乎总是由于客户端主动关闭连接导致，所以直接吃掉异常打日志
                    //比如使用video播放视频时经常会发送Range为0- 的范围只是为了获取视频大小，之后就中断连接了
//                    log.info(e.getMessage());
                }
            } else {
//                throw new RuntimeException("文件路径有误");
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("文件传输错误", e);
//            throw new RuntimeException("文件传输错误");
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("流释放错误", e);
                }
            }
            if(targetFile != null){
                try {
                    targetFile.close();
                } catch (IOException e) {
                    log.error("文件流释放错误", e);
                }
            }
        }

    }
}

