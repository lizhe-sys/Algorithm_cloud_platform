package com.bh.sfapi.service;

import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.entity.DockerContainer;
import com.bh.sfapi.entity.shangfa.Files;
import com.bh.sfapi.entity.shangfa.dto.StdModelDto;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2021/12/9 13:45
 * @desc
 */
public interface DockerApi {

    // 获取docker连接
    public DockerClient getConnect(String url);

    // 创建docker容器
    public String createContainer(String remoteIp, String remoteName, String remotePwd, String backupPath, String imageName, int userId, int containerType , int memory , int cpu );

    public void stopContainer(DockerClient client, String containerId);

    void startContainer(DockerClient dockerClient, String containerId, String remoteIp, String remoteName, String remotePwd);

    void deleteContainer(DockerClient dockerClient, String containerId, String remoteIp, String remoteName, String remotePwd, String backupPath);

    void pauseContainer(DockerClient dockerClient, String containerId);

    void unpauseContainer(DockerClient dockerClient, String containerId);

    List<DockerContainer> containerList(String remoteIp , String remoteName , String remotePwd   );

    void startDockerService(String remoteIp, String remoteName, String remotePwd);


    JSONObject getImagesCodes(String remoteIp, String remoteName, String remotePwd, String filePath , String mapName);

    void startService(String remoteIp, String remoteName, String remotePwd, String[] cmds);

    List<String> getImageByModelMgrId(String remoteIp, String remoteName, String remotePwd, String filePath);

    // 创建标准模型文件夹
    void createStdModelDir(String remoteIp, String remoteName, String remotePwd, String containerName, Long stdModelId, String stdModelDir, String jupyterStartDir);

    // 为模型创建文件夹
    void createModelDir(String remoteIp, String remoteName, String remotePwd, String modelDir, String stdDir);

    // 将数据模块文件复制到标准模型
    void copyFileToStdModel(String remoteIp, String remoteName, String remotePwd, String uploadAppendixDir , String containerName ,List<Files> files, Long stdModelId, String jupyterBackupPath);

    // 将数据模块文件复制到模型应用
    void copyFileToModelMgr(String remoteIp, String remoteName, String remotePwd, String uploadAppendixDir, String containerName, List<Files> files, Long modelId, String jupyterBackupPath);

    void updateDockerConfig(DockerClient dockerClient, String containerName, int memory, int cpu);


//    public boolean uploadFile( MultipartFile file , String backupPath , String containerId );

//    public boolean downloadFile( String backupPath, String containerName, String destfile, String localFile, String remoteIp, String remoteName, String remotePwd);
}


