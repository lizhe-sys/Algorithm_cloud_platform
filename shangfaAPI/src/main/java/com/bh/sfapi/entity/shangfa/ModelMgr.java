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
 * @create 2022/1/19 14:39
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelMgr {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "modelMgrId，模型编号",required = true )
    private Long modelMgrId;
    @ApiModelProperty(value = "dataMgrId，数据模块Id",required = true )
    private Long dataMgrId;
    private String dataMgrName;
    private Integer dataType;
    @ApiModelProperty(value = "modelType，模型类型",required = true )
    private Long modelType;
    @ApiModelProperty(value = "modelName，模型名称",required = true )
    private String modelName;
    @ApiModelProperty(value = "modelDescription，模型描述",required = true )
    private String modelDescription;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "modelResultTableName，模型输出结果",required = false )
    private String modelResultTableName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "模型创建时间",required = true )
    private Date createTime;
    @ApiModelProperty(value = "deleted，删除标记",required = true )
    private Integer deleted;
    @ApiModelProperty(value = "isShare，是否共享，1：共享，0：不共享",required = true )
    private Integer isShare;

    private String userName;
    private String stdModelName;
    private Integer stdModelType;

    @Override
    public String toString() {
        return "ModelMgr{" +
                "modelMgrId=" + modelMgrId +
                ", dataMgrId=" + dataMgrId +
                ", dataMgrName='" + dataMgrName + '\'' +
                ", modelType=" + modelType +
                ", modelName='" + modelName + '\'' +
                ", modelDescription='" + modelDescription + '\'' +
                ", userId=" + userId +
                ", modelResultTableName='" + modelResultTableName + '\'' +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                ", isShare=" + isShare +
                ", userName='" + userName + '\'' +
                ", stdModelName='" + stdModelName + '\'' +
                ", stdModelType=" + stdModelType +
                '}';
    }
}
