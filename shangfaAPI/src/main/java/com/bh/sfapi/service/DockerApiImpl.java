package com.bh.sfapi.service;

import ch.ethz.ssh2.Connection;
import com.alibaba.fastjson.JSONObject;
import com.bh.sfapi.entity.DockerContainer;
import com.bh.sfapi.entity.shangfa.Files;
import com.bh.sfapi.entity.shangfa.dto.StdModelDto;
import com.bh.sfapi.utils.CommandUtil;
import com.bh.sfapi.utils.RemoteCommandUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CommitCmd;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2021/12/9 13:46
 * @desc
 */

@Service
@Slf4j
public class DockerApiImpl implements DockerApi {


    @Value("${local-dependency-dir}")
    private String localDependencyDir;
    @Value("${jupyter-dependency-dir}")
    private String jupyterDependencyDir;
    @Value("${local-matlab-dir}")
    private String localMatlabDir;
    @Value("${jupyter-matlab-dir}")
    private String jupyterMatlabDir;

    @Override
    public DockerClient getConnect(String url) {
        DockerClient client = DockerClientBuilder.getInstance(url).build();
        return client;
    }

    // 创建容器，并将其挂载到指定的主机目录，此时不采用docker-java，采取连接主机，打开会话执行linux命令的方式
    @Override
    public String createContainer( String remoteIp, String remoteName, String remotePwd,String backupPath, String imageName, int userId, int containerType,int memory , int cpu) {

        String containerId = containerType == 1 ? "jupyter-" + userId : "vscode-" + userId;
        // jupyter和vscode容器访问端口分别从10000，20000开始
        int containerPort = containerType == 1 ? 10000 + userId : 20000 + userId;
        CommandUtil remoteCommandUtil = new CommandUtil();
        //jupyter
        if( containerType == 1 ){
            String cmd = "docker run -d -i -t -u root -p  "
                    + containerPort
                    + ":8888 --name "
                    + containerId
                    + " --memory=\"" + memory + "m\""
                    + " --cpus=\"" + cpu +"\""
                    // 挂载数据和代码文件
                    + " -v  "
                    + backupPath
                    + containerId
                    + ":/root/jupyter "
                    // 挂载配置文件路径
                    + " -v  "
                    + localDependencyDir
                    + ":" + jupyterDependencyDir + " "
                    + " -v  "
                    + localMatlabDir
                    + ":" + jupyterMatlabDir + " "
                    + imageName
                    + " /bin/bash";
            log.info("cmd={}", cmd );
            String result = remoteCommandUtil.execute(cmd);
            // 通过执行shell脚本内容（ cd /root/jupyter;jupyter lab )来实现启动jupyter
            cmd = "cp -r " + backupPath + "standard " + backupPath + containerId + ";   docker cp /root/jupyter/cmd.sh " + containerId + ":/home/cmd.sh " + ";docker exec -d " + containerId + " bin/bash /home/cmd.sh";
            remoteCommandUtil.execute( cmd);
            return result;
        }
        // vscode
        else {
            String cmd = "docker run -d -i -t -u root -p  "
                    + containerPort
                    + ":8080 --name "
                    + containerId
                    + " --memory=\"" + memory + "m\""
                    + " --cpus=\"" + cpu +"\""
                    + " -v  "
                    + backupPath
                    + containerId
                    + ":/home/coder "
                    + imageName
                    + " --auth none /bin/bash";
            String result = remoteCommandUtil.execute( cmd);
            return result;
        }
    }

    @Override
    public void stopContainer( DockerClient dockerClient, String containerId ) {
        Void exec = dockerClient.stopContainerCmd(containerId).exec();
    }

    @Override
    public void deleteContainer(  DockerClient dockerClient, String containerName,String remoteIp, String remoteName, String remotePwd, String backupPath) {

        // 1 先删除容器
        Void exec = dockerClient.removeContainerCmd(containerName)
                .withForce(true)
                .exec();

        // 2 删除主机挂载的该容器目录
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = "rm -rf " + backupPath + containerName;
        remoteCommandUtil.execute( cmd);

    }

    @Override
    public void pauseContainer(DockerClient dockerClient, String containerId) {
        Void exec = dockerClient.pauseContainerCmd(containerId)
                .exec();
    }

    @Override
    public void unpauseContainer(DockerClient dockerClient, String containerId) {
        Void exec = dockerClient.unpauseContainerCmd(containerId)
                .exec();
    }

    @Override
    public List<DockerContainer> containerList( String remoteIp, String remoteName, String remotePwd ) {

        List<DockerContainer> containers = new ArrayList<>();
//        dockerClient 获取到容器列表只包含启动之后的（运行，和睡眠状态），Existed状态的容器并没有
//        所以采取远程连接，通过命令行docker ps -a的方式获取容器列表
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = "docker ps -a";
        String result = remoteCommandUtil.execute( cmd);

//        System.out.println( result );
        String[] containerList = result.split("\n");
        if (containerList.length > 0) {
            int createdIndex = containerList[0].indexOf("CREATED");
            int statusIndex = containerList[0].indexOf("STATUS");
            int portsIndex = containerList[0].indexOf("PORTS");
            int namesIndex = containerList[0].indexOf("NAMES");
            // 每一行截取 创建时间，容器当前状态，容器名等信息
            for (int i = 1; i < containerList.length; i++) {
                String created = containerList[i].substring(createdIndex, containerList[i].indexOf("ago") + 3);
                String tmpStr = containerList[i].substring(statusIndex, portsIndex);
                String statu = tmpStr.substring(0, tmpStr.indexOf("   "));
                statu = statu.replace("Up", "已上线")
                        .replace("Paused", "睡眠状态")
                        .replace("Exited", "已下线");
                String name = containerList[i].substring(namesIndex);
                DockerContainer container = new DockerContainer();
                container.setName(name);
                container.setCreated(created);
                container.setStatu(statu);
                containers.add(container);
            }
        }
        return containers;

    }

    @Override
    public void startDockerService( String remoteIp, String remoteName, String remotePwd ) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = "systemctl start docker";
        remoteCommandUtil.execute( cmd);
    }



    @Override
    public JSONObject getImagesCodes( String remoteIp, String remoteName, String remotePwd, String filePath , String mapName) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = "cd " + filePath + "; base64 " + mapName;
        String encodes = remoteCommandUtil.execute( cmd);
        JSONObject result = new JSONObject();
        result.put( "code" , encodes );
        return result;
    }

    @Override
    public void startService(String remoteIp, String remoteName, String remotePwd,String[] cmds) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = "";
        for (int i = 0; i < cmds.length; i++) {

            if( cmds[i].contains("&") ){
                cmd = cmd + cmds[i] + "  " ;
            }
            else {
                cmd = cmd  + cmds[i] + " ; " ;
            }


        }
        String execute = remoteCommandUtil.execute(cmd);

    }

    @Override
    public List<String> getImageByModelMgrId( String remoteIp, String remoteName, String remotePwd, String filePath) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = "cd " + filePath + ";ls";
        String jpgstr = remoteCommandUtil.execute( cmd);
        List<String> jpgs = Arrays.asList(jpgstr.split("\n"));
        List<String> images = new ArrayList<>();
        for (int i = 0; i < jpgs.size(); i++) {
            String jpg = jpgs.get( i );
            if( jpg.contains( "jpg") || jpg.contains("png") ){
                images.add( jpg );
            }
        }
        return images;
    }

    @Override
    public void createStdModelDir( String remoteIp, String remoteName, String remotePwd,String containerName, Long stdModelId, String stdModelDir, String jupyterStartDir) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        // 先创建 stdmodel文件夹 再将文件移过去
        String cmd = "docker exec " + containerName + " mkdir " + jupyterStartDir + "stdmodel/;" + "docker exec " + containerName + " cp -r " + jupyterStartDir + "standard " + stdModelDir  + stdModelId;
        log.info( "cmd={}" , cmd );
        remoteCommandUtil.execute(cmd);
    }

    @Override
    public void createModelDir( String remoteIp, String remoteName, String remotePwd, String modelDir, String stdDir) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = " cp -r " + stdDir + " " + modelDir;
        log.info( "cmd={}" , cmd );
        remoteCommandUtil.execute( cmd);
    }

    @Override
    public void copyFileToStdModel(String remoteIp, String remoteName, String remotePwd,String uploadAppendixDir, String containerName, List<Files> files, Long stdModelId, String jupyterBackupPath) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        // 根据文件路径，拼接出文件存储路径
        for (Files file: files ) {
            String path = file.getFile_path();
            path = uploadAppendixDir + path;
            String cmd = " cp -r " + path + " " + jupyterBackupPath + containerName + "/stdmodel/" + stdModelId + "/querydata/" + file.getFile_name();
            remoteCommandUtil.execute( cmd);
        }
    }

    @Override
    public void copyFileToModelMgr(String remoteIp, String remoteName, String remotePwd, String uploadAppendixDir, String containerName, List<Files> files, Long modelId, String jupyterBackupPath) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        // 根据文件路径，拼接出文件存储路径
        for (Files file: files ) {
            String path = file.getFile_path();
            path = uploadAppendixDir + path;
            String cmd = " cp -r " + path + " " + jupyterBackupPath + containerName + "/"+ modelId + "/querydata/" + file.getFile_name();
            remoteCommandUtil.execute( cmd);
        }
    }

    @Override
    public void updateDockerConfig(DockerClient dockerClient, String containerName, int memory, int cpu) {
        CommandUtil remoteCommandUtil = new CommandUtil();
        String cmd = "docker container update " + containerName + " --memory=\"" + memory + "m\" --cpus=\"" + cpu + "\"";
        remoteCommandUtil.execute( cmd );
    }

    @Override
    public void startContainer( DockerClient dockerClient, String containerId ,  String remoteIp, String remoteName, String remotePwd) {

        //先启动容器
        Void exec = dockerClient.startContainerCmd(containerId).exec();
        // 判断是否为jupyter,再决定是不是执行jupyter启动命令
        if (containerId.indexOf("jupyter") != -1) {
            // 在容器内启动jupyter服务
            CommandUtil remoteCommandUtil = new CommandUtil();
            // 通过执行shell脚本内容（ cd /root/jupyter;jupyter lab )来实现启动jupyter
            // -d参数实现启动之后连接关闭，不至于一直开始会话窗口，接口无法退出
            String cmd = "docker cp /root/jupyter/cmd.sh " + containerId + ":/home/cmd.sh " + ";docker exec -d " + containerId + " bin/bash /home/cmd.sh ";
            String result = remoteCommandUtil.execute(cmd);
        }
    }
}


/**
 * 方便本地调试 使用的远程连接方式
 */
//package com.bh.sfapi.service;
//
//import ch.ethz.ssh2.Connection;
//import com.alibaba.fastjson.JSONObject;
//import com.bh.sfapi.entity.DockerContainer;
//import com.bh.sfapi.entity.shangfa.Files;
//import com.bh.sfapi.entity.shangfa.dto.StdModelDto;
//import com.bh.sfapi.utils.CommandUtil;
//import com.bh.sfapi.utils.RemoteCommandUtil;
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.command.CommitCmd;
//import com.github.dockerjava.core.DockerClientBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import sun.misc.BASE64Decoder;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author Administrator
// * @version 1.0
// * @create 2021/12/9 13:46
// * @desc
// */
//
//@Service
//@Slf4j
//public class DockerApiImpl implements DockerApi {
//
//
//    @Override
//    public DockerClient getConnect(String url) {
//        DockerClient client = DockerClientBuilder.getInstance(url).build();
//        return client;
//    }
//
//    // 创建容器，并将其挂载到指定的主机目录，此时不采用docker-java，采取连接主机，打开会话执行linux命令的方式
//    @Override
//    public String createContainer(String remoteIp, String remoteName, String remotePwd, String backupPath, String imageName, int userId, int containerType) {
//
//        String containerId = containerType == 1 ? "jupyter-" + userId : "vscode-" + userId;
//        // jupyter和vscode容器访问端口分别从10000，20000开始
//        int containerPort = containerType == 1 ? 10000 + userId : 20000 + userId;
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        //jupyter
//        if( containerType == 1 ){
//            String cmd = "docker run -d -i -t -u root -p  "
//                    + containerPort
//                    + ":8888 --name "
//                    + containerId
//                    + " -v  "
//                    + backupPath
//                    + containerId
//                    + ":/root/jupyter "
//                    + imageName
//                    + " /bin/bash";
//            String result = remoteCommandUtil.execute(connection, cmd);
//            // 通过执行shell脚本内容（ cd /root/jupyter;jupyter lab )来实现启动jupyter
//
//            cmd = "cp -r " + backupPath + "standard " + backupPath + containerId + ";   docker cp /root/jupyter/cmd.sh " + containerId + ":/home/cmd.sh " + ";docker exec -d " + containerId + " bin/bash /home/cmd.sh ";
//            remoteCommandUtil.execute(connection, cmd);
//            connection.close();
//            return result;
//        }
//        // vscode
//        else {
//            String cmd = "docker run -d -i -t -u root -p  "
//                    + containerPort
//                    + ":8080 --name "
//                    + containerId
//                    + " -v  "
//                    + backupPath
//                    + containerId
//                    + ":/home/coder "
//                    + imageName
//                    + " --auth none /bin/bash";
//            String result = remoteCommandUtil.execute(connection, cmd);
//            connection.close();
//            return result;
//        }
//    }
//
//    @Override
//    public void stopContainer( DockerClient dockerClient, String containerId ) {
//        Void exec = dockerClient.stopContainerCmd(containerId).exec();
//    }
//
//    @Override
//    public void deleteContainer(DockerClient dockerClient, String containerName, String remoteIp, String remoteName, String remotePwd, String backupPath) {
//
//        // 1 先删除容器
//        Void exec = dockerClient.removeContainerCmd(containerName)
//                .withForce(true)
//                .exec();
//
//        // 2 删除主机挂载的该容器目录
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = "rm -rf " + backupPath + containerName;
//        remoteCommandUtil.execute(connection, cmd);
//        connection.close();
//
//    }
//
//    @Override
//    public void pauseContainer(DockerClient dockerClient, String containerId) {
//        Void exec = dockerClient.pauseContainerCmd(containerId)
//                .exec();
//    }
//
//    @Override
//    public void unpauseContainer(DockerClient dockerClient, String containerId) {
//        Void exec = dockerClient.unpauseContainerCmd(containerId)
//                .exec();
//    }
//
//    @Override
//    public List<DockerContainer> containerList(String remoteIp, String remoteName, String remotePwd) {
//
//        List<DockerContainer> containers = new ArrayList<>();
////        dockerClient 获取到容器列表只包含启动之后的（运行，和睡眠状态），Existed状态的容器并没有
////        所以采取远程连接，通过命令行docker ps -a的方式获取容器列表
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = "docker ps -a";
//        String result = remoteCommandUtil.execute(connection, cmd);
//
////        System.out.println( result );
//        String[] containerList = result.split("\n");
//        if (containerList.length > 0) {
//            int createdIndex = containerList[0].indexOf("CREATED");
//            int statusIndex = containerList[0].indexOf("STATUS");
//            int portsIndex = containerList[0].indexOf("PORTS");
//            int namesIndex = containerList[0].indexOf("NAMES");
//            // 每一行截取 创建时间，容器当前状态，容器名等信息
//            for (int i = 1; i < containerList.length; i++) {
//                String created = containerList[i].substring(createdIndex, containerList[i].indexOf("ago") + 3);
//                String tmpStr = containerList[i].substring(statusIndex, portsIndex);
//                String statu = tmpStr.substring(0, tmpStr.indexOf("   "));
//                statu = statu.replace("Up", "已上线")
//                        .replace("Paused", "睡眠状态")
//                        .replace("Exited", "已下线");
//                String name = containerList[i].substring(namesIndex);
//                DockerContainer container = new DockerContainer();
//                container.setName(name);
//                container.setCreated(created);
//                container.setStatu(statu);
//                containers.add(container);
//            }
//        }
//        return containers;
//
//    }
//
//    @Override
//    public void startDockerService(String remoteIp, String remoteName, String remotePwd) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = "systemctl start docker";
//        remoteCommandUtil.execute(connection, cmd);
//        connection.close();
//    }
//
//
//
//    @Override
//    public JSONObject getImagesCodes(String remoteIp, String remoteName, String remotePwd, String filePath , String mapName) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = "cd " + filePath + "; base64 " + mapName;
//        String encodes = remoteCommandUtil.execute(connection, cmd);
//        JSONObject result = new JSONObject();
//        result.put( "code" , encodes );
//        return result;
//    }
//
//    @Override
//    public void startService(String remoteIp, String remoteName, String remotePwd, String[] cmds) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = "";
//        for (int i = 0; i < cmds.length; i++) {
//
//            if( cmds[i].contains("&") ){
//                cmd = cmd + cmds[i] + "  " ;
//            }
//            else {
//                cmd = cmd  + cmds[i] + " ; " ;
//            }
//
//
//        }
//        String execute = remoteCommandUtil.execute(connection, cmd);
////        System.out.println( execute );
//        connection.close();
//
//    }
//
//    @Override
//    public List<String> getImageByModelMgrId(String remoteIp, String remoteName, String remotePwd, String filePath) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = "cd " + filePath + ";ls";
//        String jpgstr = remoteCommandUtil.execute(connection, cmd);
//        List<String> jpgs = Arrays.asList(jpgstr.split("\n"));
//        List<String> images = new ArrayList<>();
//        for (int i = 0; i < jpgs.size(); i++) {
//            String jpg = jpgs.get( i );
//            if( jpg.contains( "jpg") || jpg.contains("png") ){
//                images.add( jpg );
//            }
//        }
//        return images;
//    }
//
//    @Override
//    public void createStdModelDir(String remoteIp, String remoteName, String remotePwd, String containerName, Long stdModelId, String stdModelDir, String jupyterStartDir) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = "docker exec " + containerName + " cp -r " + jupyterStartDir + "standard " + stdModelDir  + stdModelId;
//        remoteCommandUtil.execute(connection, cmd);
//        connection.close();
//    }
//
//    @Override
//    public void createModelDir(String remoteIp, String remoteName, String remotePwd, String modelDir, String stdDir) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        String cmd = " cp -r " + stdDir + " " + modelDir;
//        remoteCommandUtil.execute(connection, cmd);
//        connection.close();
//    }
//
//    @Override
//    public void copyFileToStdModel(String remoteIp, String remoteName, String remotePwd,String uploadAppendixDir, String containerName, List<Files> files, Long stdModelId, String jupyterBackupPath) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        // 根据文件路径，拼接出文件存储路径
//        List<String> filePaths = new ArrayList<>();
//
//        for (Files file: files ) {
//            String path = file.getFile_path();
//            path = uploadAppendixDir + path;
//            String cmd = " cp -r " + path + " " + jupyterBackupPath + containerName + "/stdmodel/" + stdModelId + "/querydata/" + file.getFile_name();
////            System.out.println( cmd );
//            remoteCommandUtil.execute( connection, cmd);
//        }
//        connection.close();
//    }
//
//    @Override
//    public void copyFileToModelMgr(String remoteIp, String remoteName, String remotePwd, String uploadAppendixDir, String containerName, List<Files> files, Long modelId, String jupyterBackupPath) {
//        RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//        Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//        // 根据文件路径，拼接出文件存储路径
//        List<String> filePaths = new ArrayList<>();
//
//        for (Files file: files ) {
//            String path = file.getFile_path();
//            path = uploadAppendixDir + path;
//            String cmd = " cp -r " + path + " " + jupyterBackupPath + containerName + "/" + modelId + "/querydata/" + file.getFile_name();
////            System.out.println( cmd );
//            remoteCommandUtil.execute( connection, cmd);
//        }
//        connection.close();
//    }
//
//    @Override
//    public void startContainer(DockerClient dockerClient, String containerId, String remoteIp, String remoteName, String remotePwd) {
//
//        //先启动容器
//        Void exec = dockerClient.startContainerCmd(containerId).exec();
//        // 判断是否为jupyter,再决定是不是执行jupyter启动命令
//        if (containerId.indexOf("jupyter") != -1) {
//            // 在容器内启动jupyter服务
//            RemoteCommandUtil remoteCommandUtil = new RemoteCommandUtil();
//            Connection connection = remoteCommandUtil.login(remoteIp, remoteName, remotePwd);
//            // 通过执行shell脚本内容（ cd /root/jupyter;jupyter lab )来实现启动jupyter
//            // -d参数实现启动之后连接关闭，不至于一直开始会话窗口，接口无法退出
//            String cmd = "docker cp /root/jupyter/cmd.sh " + containerId + ":/home/cmd.sh " + ";docker exec -d " + containerId + " bin/bash /home/cmd.sh ";
//            String result = remoteCommandUtil.execute(connection, cmd);
////            System.out.println(result);
//            connection.close();
//        }
//    }
//}


