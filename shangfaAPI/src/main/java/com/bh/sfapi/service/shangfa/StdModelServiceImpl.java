package com.bh.sfapi.service.shangfa;

import com.alibaba.fastjson.asm.FieldWriter;
import com.bh.sfapi.dao.ModelMgrDAO;
import com.bh.sfapi.dao.StdModelDAO;
import com.bh.sfapi.entity.shangfa.ModelMgr;
import com.bh.sfapi.entity.shangfa.StdModel;
import com.bh.sfapi.entity.shangfa.dto.StdModelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/12 14:12
 * @desc
 */

@Service
@Transactional
@Slf4j
public class StdModelServiceImpl implements StdModelService {

    @Autowired
    StdModelDAO stdModelDAO;
    @Autowired
    ModelMgrService modelMgrService;
    @Autowired
    HealthMgrService healthMgrService;

    @Override
    public void addStdModelMgr(StdModel stdModel) {
        stdModelDAO.addStdModelMgr( stdModel );
    }

    @Override
    public StdModel queryNewStdModel(Long userId) {
        return stdModelDAO.queryNewStdModel( userId );
    }

    @Override
    public void updateStdModel(StdModel stdModel) {
        stdModelDAO.updateStdModel( stdModel );
    }


    @Override
    public List<StdModel> getStdModelList() {
        return stdModelDAO.getStdModelList();
    }

    @Override
    public List<StdModel> dimQueryStdModel(StdModel stdModel) {
        return stdModelDAO.dimQueryStdModel( stdModel );
    }

    @Override
    public StdModel queryStdModelById(Long stdModelId) {
        return stdModelDAO.queryStdModelById( stdModelId );
    }

    @Override
    public StdModelDto getPackStdModelById(long id) {
        return stdModelDAO.getPackStdModelById( id );
    }

    @Override
    public StdModelDto updatePackStdModel(StdModelDto stdModel , String jupyterBackupPath ) {

        // 获取到字段信息
        List<String> responseParams = stdModel.getReponseParams();
        List<String> requestParams = stdModel.getRequestParams();
        List<String> requestParamRemarks = stdModel.getRequestParamRemarks();
        List<String> responseParamRemaks = stdModel.getResponseParamRemaks();

        String responseParam = null , responseParamRemak = null , requestParam = null , requestParamRemark = null;
        for (int i = 0; responseParams != null && i < responseParams.size(); i++) {
            if( i == 0 ) responseParam = responseParams.get( i );
            else {
                responseParam += "&" + responseParams.get( i );
            }
        }
        for (int i = 0; requestParams != null && i < requestParams.size(); i++) {
            if( i == 0 ) requestParam = requestParams.get( i );
            else {
                requestParam += "&" + requestParams.get( i );
            }
        }
        for (int i = 0; requestParamRemarks != null && i < requestParamRemarks.size(); i++) {
            if( i == 0 ) requestParamRemark = requestParamRemarks.get( i );
            else {
                requestParamRemark += "&" + requestParamRemarks.get( i );
            }
        }
        for (int i = 0; responseParamRemaks != null && i < responseParamRemaks.size(); i++) {
            if( i == 0 ) responseParamRemak = responseParamRemaks.get( i );
            else {
                responseParamRemak += "&" + responseParamRemaks.get( i );
            }
        }
        stdModel.setRequestParam( requestParam );
        stdModel.setResponseParam( responseParam );
        stdModel.setRequestParamRemark( requestParamRemark );
        stdModel.setResponseParamRemak( responseParamRemak );
        // 将记录更新到数据库
        stdModelDAO.updatePackStdModel( stdModel );
        // 获取拼接内容
        String content = "";
        content = getContent( stdModel );
//        System.out.println( content );
        // 将内容更新到jupyter
//        String filePath =  System.getProperty("user.dir") + "\\packmodel.py";
        String filePath = jupyterBackupPath + "jupyter-" + stdModel.getUserId() + "/stdmodel/" + stdModel.getStdModelId() +  "/packmodel.py";

        try {
            File file = new File( filePath );
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write( content );
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stdModel;
    }

    @Override
    public StdModel queryStdModelByName(String stdModelName) {
        return stdModelDAO.queryStdModelByName( stdModelName );
    }


    // 删除标准模型时，需要同步删除模型应用及健康记录
    @Override
    public void deleteStdModel(StdModel stdModel) {

        // 查询出与标准模型相关联的模型列表
        List<ModelMgr> modelMgrs = modelMgrService.getModelMgrListByStdModelId( stdModel.getStdModelId() );
        for (ModelMgr modelMgr: modelMgrs) {
            modelMgrService.deleteModelMgr( modelMgr.getModelMgrId().toString() );
            healthMgrService.deleteHealthMgrByModelMgrId( modelMgr.getModelMgrId().toString() );
        }
        stdModelDAO.updateStdModel( stdModel );
    }

    @Override
    public List<StdModel> getSharedStdModelList(Long userId) {
        return stdModelDAO.getSharedStdModelList( userId );
    }

    @Override
    public List<StdModel> getStdModelListInfo() {
        return stdModelDAO.getStdModelListInfo();
    }

    public String getContent( StdModelDto stdModel ){
        String content = "";
        if( stdModel.getStdModelName() != null )
            content = content + "模型名称：\n" + stdModel.getStdModelName() +"\n\n";
        if( stdModel.getStdModelDes() != null )
            content = content + "模型描述：\n" + stdModel.getStdModelDes() + "\n\n";
        if( stdModel.getAddr() != null )
            content = content + "接口地址：\n" +stdModel.getAddr() + "\n\n";
        if( stdModel.getProtocol() != null )
            content = content + "通讯协议：\n" +stdModel.getProtocol() + "\n\n";
        // 获取到字段信息
        List<String> responseParams = stdModel.getReponseParams();
        List<String> requestParams = stdModel.getRequestParams();
        List<String> requestParamRemarks = stdModel.getRequestParamRemarks();
        List<String> responseParamRemaks = stdModel.getResponseParamRemaks();

        content = content + "输入参数：\n";
        for (int i = 0; requestParams != null && i < requestParams.size(); i++) {
//            content = content + "参数名称：" +requestParams.get( i ) + "\t" + "参数说明：" + requestParamRemarks.get( i ) + "\n";
            content = content + "参数名称：" +requestParams.get( i ) + "(说明：" + requestParamRemarks.get( i ) + ")\n";
        }

        content = content + "\n响应参数：\n";
        for (int i = 0; responseParams != null && i < responseParams.size(); i++) {
//            content = content + "参数名称：" + responseParams.get( i ) + "\t" + "参数说明：" + responseParamRemaks.get( i ) + "\n";
            content = content + "参数名称：" +responseParams.get( i ) + "(说明：" + responseParamRemaks.get( i ) + ")\n";
        }

        content = "'''\n" + content + "\n'''\n";

        content = content + "#接口请求模板\n" + "import requests\n" + "import json, time\n" + "import utils\n\n";
        content = content +
                "def getContent( param1 , param2 ):\n" +
                "    url = \"接口地址\" + \"param1=\" + param1 + \"&param2=\" + param2\n" +
                "    value = requests.get(url)\n" +
                "    value = value.text\n" +
                "    # 对返回的数据进行处理\n" +
                "    res = []\n" +
                "    res.append( result )\n" +
                "    res.append( result1 )\n" +
                "    \n" +
                "    return res";
        return content;

    }
}
