package com.bh.sfapi.controller;


import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.entity.DockerContainer;
import com.bh.sfapi.entity.shangfa.DataMgr;
import com.bh.sfapi.entity.shangfa.StdModel;
import com.bh.sfapi.entity.shangfa.User;
import com.bh.sfapi.result.RestMessage;
import com.bh.sfapi.result.Result;
import com.bh.sfapi.service.DockerApi;
import com.bh.sfapi.service.DockerApiImpl;
import com.bh.sfapi.service.shangfa.LogRecordService;
import com.bh.sfapi.service.shangfa.StdModelService;
import com.bh.sfapi.service.shangfa.UserServiceApi;
import com.github.dockerjava.api.DockerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/8/11 10:18
 * @desc 针对于docker开发相关操作接口，该后台和docker运行在同一服务端
 *       针对于docker的相关操作，可以转变为，在该服务端进行的相关linux命令操作
 */


@EnableAutoConfiguration
@Api(tags = "docker操作")
@RestController
@RequestMapping("base/docker")
@CrossOrigin
@Slf4j
public class DockerController {

    //docker连接地址
    @Value("${dockerUrl}")
    private String dockerUrl;
    // 远程连接ip
    @Value("${remoteIp}")
    private String remoteIp;
    // 远程连接用户名
    @Value("${remoteName}")
    private String remoteName;
    // 远程连接密码
    @Value("${remotePwd}")
    private String remotePwd;

    // 容器启动路径
    @Value("${jupyterStartDir}")
    private String jupyterStartDir;
    // 标准模型路径
    @Value("${stdModelDir}")
    private String stdModelDir;
    // 容器挂载主目录
    @Value("${backupPath}")
    private String backupPath;
    @Value("${jupyterBackupPath}")
    private String jupyterBackupPath;
    @Value("${vscodeBackupPath}")
    private String vscodeBackupPath;

    // 镜像名称
    @Value("${imageName}")
    private String imageName;
    @Value("${jupyterImageName}")
    private String jupyterImageName;
    @Value("${vscodeImageName}")
    private String vscodeImageName;
    // 容器类型，1 jupyter; 2 vscode
    private int containerType;

    @Autowired
    DockerApi dockerApi;
    @Autowired
    StdModelService stdModelService;
    @Autowired
    UserServiceApi userServiceApi;
    @Autowired
    LogRecordService logRecordService;



    public String getCookie( String name , HttpServletRequest request ){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if( "userId".equals( cookie.getName() ) ){
                return cookie.getValue();
            }
        }
        return null;
    }

    //开启docker服务
    @ApiOperation( value = "开启docker服务")
    @GetMapping("startDockerService")
    public RestMessage startDockerService(){
        dockerApi.startDockerService( remoteIp, remoteName, remotePwd );
        return new RestMessage();
    }

    // 创建链接
    public DockerClient getConnect(){
        DockerClient dockerClient = dockerApi.getConnect(dockerUrl);
        return dockerClient;
    }

    // 创建容器
    @ApiOperation( value = "创建容器并挂载到主机指定目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true),
            @ApiImplicitParam(name = "memory",value = "容器内存大小，单位：m",required = true),
            @ApiImplicitParam(name = "cpu",value = "容器分配的cpu数量",required = true)
    })
    @GetMapping("createContainer")
    public RestMessage createContainer( int userId, int containerType, int memory , int cpu , HttpServletRequest request ){
        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        backupPath = containerType == 1 ? jupyterBackupPath : vscodeBackupPath;
        imageName = containerType == 1 ? jupyterImageName : vscodeImageName;


        User user = userServiceApi.getUserByUserId( (long)userId );

        // 更新用户容器配置信息
        user.setContainerAllocate( 1 );
        user.setContainerMemory( memory );
        user.setContainerCpu( cpu );
        userServiceApi.updateUser( user );

        String value = getCookie("userId" , request );
        Long adminUserId =  value == null ? null : Long.parseLong( value );
        if( adminUserId != null ){
            String operateDesc = "新增用户" + user.getUserName() + "的算法空间";
            logRecordService.addLog( adminUserId , "docker" , operateDesc );
        }

        String container = dockerApi.createContainer( remoteIp, remoteName, remotePwd, backupPath, imageName, userId, containerType , memory , cpu );
        return new RestMessage( container );

    }

    // 停止容器
    @ApiOperation("修改容器配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true),
            @ApiImplicitParam(name = "memory",value = "容器内存大小，单位：m",required = true),
            @ApiImplicitParam(name = "cpu",value = "容器分配的cpu数量",required = true)
    })
    @GetMapping("updateDockerConfig")
    public RestMessage updateDockerConfig(  int userId, int containerType , int memory , int cpu, HttpServletRequest request ){

        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        String containerName = containerType == 1 ? "jupyter-"+userId : "vscode-"+userId;
        User user = userServiceApi.getUserByUserId( (long)userId );
        // 更新用户容器配置信息
        user.setContainerMemory( memory );
        user.setContainerCpu( cpu );
        userServiceApi.updateUser( user );

        String value = getCookie("userId" , request );
        Long adminUserId =  value == null ? null : Long.parseLong( value );
        if( adminUserId != null ){
            String operateDesc = "编辑用户" + user.getUserName() + "的算法空间";
            logRecordService.addLog( adminUserId , "docker" , operateDesc );
        }
        DockerClient dockerClient = dockerApi.getConnect(dockerUrl);

        dockerApi.updateDockerConfig( dockerClient, containerName, memory , cpu );
        return new RestMessage();
    }

    // 停止容器
    @ApiOperation("停止容器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true)
    })
    @GetMapping("stopContainer")
    public RestMessage stopContainer(  int userId, int containerType , HttpServletRequest request ){

        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        String containerName = containerType == 1 ? "jupyter-"+userId : "vscode-"+userId;

        User user = userServiceApi.getUserByUserId( (long)userId );

        String value = getCookie("userId" , request );
        Long adminUserId =  value == null ? null : Long.parseLong( value );
        if( adminUserId != null ){
            String operateDesc = "停止用户" + user.getUserName() + "的算法空间";
            logRecordService.addLog( adminUserId , "docker" , operateDesc );
        }

        DockerClient dockerClient = dockerApi.getConnect(dockerUrl);
        dockerApi.stopContainer(dockerClient, containerName);
        return new RestMessage();
    }

    // 启动容器（分为jupyter启动和vscode启动）
    @ApiOperation("启动容器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true)
    })
    @GetMapping("startContainer")
    public RestMessage startContainer(  int userId, int containerType , HttpServletRequest request  ){
        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        String containerName = containerType == 1 ? "jupyter-"+userId : "vscode-"+userId;

        User user = userServiceApi.getUserByUserId( (long)userId );
        String value = getCookie("userId" , request );
        Long adminUserId =  value == null ? null : Long.parseLong( value );
        if( adminUserId != null ){
            String operateDesc = "启动用户" + user.getUserName() + "的算法空间";
            logRecordService.addLog( adminUserId , "docker" , operateDesc );
        }

        DockerClient dockerClient = dockerApi.getConnect(dockerUrl);
        dockerApi.startContainer( dockerClient, containerName , remoteIp , remoteName , remotePwd );
        return new RestMessage();
    }

    // 删除容器
    @ApiOperation("删除容器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true)
    })
    @DeleteMapping("deleteContainer")
    public RestMessage deleteContainer(  int userId, int containerType , HttpServletRequest request  ){
        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        String containerName = containerType == 1 ? "jupyter-"+userId : "vscode-"+userId;
        backupPath = containerType == 1 ? jupyterBackupPath : vscodeBackupPath;

        User user = userServiceApi.getUserByUserId( (long)userId );
        // 设置用户容器删除
        user.setContainerAllocate( 0 );
        user.setContainerMemory( 0 );
        user.setContainerCpu( 0 );
        userServiceApi.updateUser( user );

        String value = getCookie("userId" , request );
        Long adminUserId =  value == null ? null : Long.parseLong( value );
        if( adminUserId != null ){
            String operateDesc = "删除用户" + user.getUserName() + "的算法空间";
            logRecordService.addLog( adminUserId , "docker" , operateDesc );
        }

        DockerClient dockerClient = dockerApi.getConnect(dockerUrl);
        // 再删除容器,及挂载到主机的目录
        dockerApi.deleteContainer( dockerClient , containerName , remoteIp ,remoteName , remotePwd , backupPath );
        return new RestMessage();
    }

    // 唤醒容器
    @ApiOperation("唤醒容器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true)
    })
    @GetMapping("unPauseContainer")
    public RestMessage unPauseContainer( int userId, int containerType , HttpServletRequest request  ){
        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        String containerName = containerType == 1 ? "jupyter-"+userId : "vscode-"+userId;

        User user = userServiceApi.getUserByUserId( (long)userId );
        String value = getCookie("userId" , request );
        Long adminUserId =  value == null ? null : Long.parseLong( value );
        if( adminUserId != null ){
            String operateDesc = "唤醒用户" + user.getUserName() + "的算法空间";
            logRecordService.addLog( adminUserId , "docker" , operateDesc );
        }


        DockerClient dockerClient = dockerApi.getConnect(dockerUrl);
        dockerApi.unpauseContainer( dockerClient , containerName );
        return new RestMessage();
    }

    // 容器睡眠
    @ApiOperation("容器睡眠")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true)
    })
    @GetMapping("pauseContainer")
    public RestMessage pauseContainer( int userId, int containerType , HttpServletRequest request  ){
        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        String containerName = containerType == 1 ? "jupyter-"+userId : "vscode-"+userId;

        User user = userServiceApi.getUserByUserId( (long)userId );
        String value = getCookie("userId" , request );
        Long adminUserId =  value == null ? null : Long.parseLong( value );
        if( adminUserId != null ){
            String operateDesc = "睡眠用户" + user.getUserName() + "的算法空间";
            logRecordService.addLog( adminUserId , "docker" , operateDesc );
        }

        DockerClient dockerClient = dockerApi.getConnect(dockerUrl);
        dockerApi.pauseContainer( dockerClient , containerName );
        return new RestMessage();
    }

    @ApiOperation("创建模型文件夹空间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true),
            @ApiImplicitParam(name = "containerType",value = "容器类型，1 jupyter; 2 vscode",required = true),
            @ApiImplicitParam( name = "modelId" , value = "模型Id" ,required = true ),
            @ApiImplicitParam( name = "modelId" , value = "模型Id" ,required = true )
    })
    @GetMapping("createModelDir")
    public RestMessage createModelDir( long userId , int containerType , long modelId , long modelType ){
        if( containerType != 1 && containerType != 2 ){
            return new RestMessage(false , "容器类型未选择"  );
        }
        String containerName = containerType == 1 ? "jupyter-"+userId : "vscode-"+userId;
        // 根据标准模型类型 查出标准模型信息
        StdModel stdModel = stdModelService.queryStdModelById( modelType );
        // 根据标准模型信息，获取到复用的标准模型路径
        String stdDir = jupyterBackupPath + "jupyter-" + stdModel.getUserId() + "/stdmodel/" + stdModel.getStdModelId();
        String modelDir = jupyterBackupPath + containerName + "/" + modelId;
        dockerApi.createModelDir( remoteIp , remoteName , remotePwd , modelDir, stdDir );
        return new RestMessage();
    }

    @ApiOperation("获取容器挂载目录下的全部图片文件编码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath",value = "文件路径",required = true),
            @ApiImplicitParam(name = "mapName", value = "图谱名称", required = true)
    })
    @GetMapping("getImagesCodes")
    public JSONObject  getImagesCodes( String filePath , String mapName ){
        JSONObject imagesCodes = dockerApi.getImagesCodes(remoteIp, remoteName, remotePwd, filePath, mapName );
        return imagesCodes;
    }

    // 获取容器列表
    @ApiOperation("获取容器列表")
    @GetMapping("containerList")
    public RestMessage containerList() {
        List<DockerContainer> containerList = dockerApi.containerList( remoteIp , remoteName , remotePwd );
        return new RestMessage( containerList );
    }

    @ApiOperation("启动阿里云部署的服务")
    @GetMapping("startService")
    public Result startService() {

        String[] cmds = {
                "cd /root/sf/sfapi",
                "nohup java -jar shangfaAPI-0.0.1-SNAPSHOT.jar >msg.log 2>&1 &",
                "cd /root/sf/filetools",
                "nohup java -jar vip-file-upload.jar >msg.log 2>&1 &",
                "cd /myproject/bill",
                "nohup java -jar account-0.0.1-SNAPSHOT.jar >msg.log 2>&1 &",
                "cd /myproject/watchCamera",
                "nohup java -jar kafka_s6-0.0.1-SNAPSHOT.jar >msg.log 2>&1 &",
                "systemctl start docker"
        };

        dockerApi.startService( remoteIp , remoteName , remotePwd , cmds );


        return Result.ok();
    }


    public static void main(String[] args) {
        DockerController dockerController = new DockerController();
    }

}