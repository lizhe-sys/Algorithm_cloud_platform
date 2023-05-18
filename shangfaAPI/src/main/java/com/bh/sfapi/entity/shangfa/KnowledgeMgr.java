package com.bh.sfapi.entity.shangfa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/12 14:00
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KnowledgeMgr {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "knowledgeMgrId，知识编号",required = false )
    private Long knowledgeMgrId;
    @ApiModelProperty(value = "dataMgrId，实验编号",required = false )
    private Long dataMgrId;
    @ApiModelProperty(value = "modelMgrId，模型编号",required = false )
    private Long modelMgrId;
    @ApiModelProperty(value = "faultPointName，故障测点名",required = false )
    private String faultPointName;
    @ApiModelProperty(value = "faultDescription，故障描述",required = false )
    private String faultDescription;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    @ApiModelProperty(value = "faultStartTime，故障起始时间",required = false )
    private Date faultStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    @ApiModelProperty(value = "faultEndTime，故障截止时间",required = false )
    private Date faultEndTime;
    @ApiModelProperty(value = "knowledgeType，知识类型",required = false )
    private String knowledgeType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "知识创建时间",required = true )
    private Date createTime;
    @ApiModelProperty(value = "用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "是否删除",required = true )
    private Integer deleted;

    @Override
    public String toString() {
        return "KnowledgeMgr{" +
                "knowledgeMgrId=" + knowledgeMgrId +
                ", dataMgrId=" + dataMgrId +
                ", modelMgrId=" + modelMgrId +
                ", faultPointName='" + faultPointName + '\'' +
                ", faultDescription='" + faultDescription + '\'' +
                ", faultStartTime=" + faultStartTime +
                ", faultEndTime=" + faultEndTime +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", deleted=" + deleted +
                '}';
    }
}
