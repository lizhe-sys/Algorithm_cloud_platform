package com.bh.sfapi.entity.shangfa.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/26 16:23
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StdModelDto {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "stdModelId，标准模板编号",required = true )
    private Long stdModelId;
    @ApiModelProperty(value = "stdModelType，标准模板类型，1：在线模型，2：封装模型",required = true )
    private Integer stdModelType;
    @ApiModelProperty(value = "stdModelName，标准模板名称",required = true )
    private String stdModelName;
    @ApiModelProperty(value = "dataMgrId，数据模块Id",required = true )
    private Long dataMgrId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "模型创建时间",required = true )
    private Date createTime;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "stdModelDes，标准模型描述",required = true )
    private String stdModelDes;
    @ApiModelProperty(value = "addr，接口地址",required = true )
    private String addr;
    @ApiModelProperty(value = "protocol，通信协议",required = true )
    private String protocol;
    @JsonIgnore
    private String requestParam;
    @JsonIgnore
    private String requestParamRemark;
    @JsonIgnore
    private String responseParam;
    @JsonIgnore
    private String responseParamRemak;
    @ApiModelProperty(value = "isShare，是否共享，1：共享，0：不共享",required = true )
    private Long isShare;
    @ApiModelProperty(value = "deleted，删除标记",required = true )
    private Integer deleted;
    @ApiModelProperty(value = "score，模型评分",required = false )
    private Float score;
    @ApiModelProperty(value = "fileId，接口文档Id",required = false )
    private String fileId;
    @ApiModelProperty(value = "callCount，调用次数",required = false )
    private Integer callCount;

    private String userName;
    private String dataMgrName;
    @ApiModelProperty(value = "requestParams，请求参数列表",required = true )
    private List<String> requestParams;
    @ApiModelProperty(value = "requestParamRemarks，请求参数说明列表",required = true )
    private List<String> requestParamRemarks;
    @ApiModelProperty(value = "reponseParams，返回参数列表",required = true )
    private List<String> reponseParams;
    @ApiModelProperty(value = "responseParamRemaks，返回参数说明列表",required = true )
    private List<String> responseParamRemaks;

    @Override
    public String toString() {
        return "StdModelDto{" +
                "stdModelId=" + stdModelId +
                ", stdModelType=" + stdModelType +
                ", stdModelName='" + stdModelName + '\'' +
                ", dataMgrId=" + dataMgrId +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", stdModelDes='" + stdModelDes + '\'' +
                ", addr='" + addr + '\'' +
                ", protocol='" + protocol + '\'' +
                ", requestParam='" + requestParam + '\'' +
                ", requestParamRemark='" + requestParamRemark + '\'' +
                ", responseParam='" + responseParam + '\'' +
                ", responseParamRemak='" + responseParamRemak + '\'' +
                ", isShare=" + isShare +
                ", deleted=" + deleted +
                ", score=" + score +
                ", fileId='" + fileId + '\'' +
                ", callCount=" + callCount +
                ", userName='" + userName + '\'' +
                ", dataMgrName='" + dataMgrName + '\'' +
                ", requestParams=" + requestParams +
                ", requestParamRemarks=" + requestParamRemarks +
                ", reponseParams=" + reponseParams +
                ", responseParamRemaks=" + responseParamRemaks +
                '}';
    }
}
