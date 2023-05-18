package com.bh.sfapi.entity.shangfa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/25 20:52
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StdModel {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "stdModelId，标准模板编号",required = true )
    private Long stdModelId;
    @ApiModelProperty(value = "stdModelType，标准模板类型，1：在线模型，2：封装模型",required = true )
    private Integer stdModelType;
    @ApiModelProperty(value = "stdModelName，标准模板名称",required = true )
    private String stdModelName;
    @ApiModelProperty(value = "stdModelDes，标准模板介绍",required = true )
    private String stdModelDes;
    @ApiModelProperty(value = "dataMgrId，数据模块Id",required = true )
    private Long dataMgrId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "模型创建时间",required = true )
    private Date createTime;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "isShare，是否共享，1：共享，0：不共享",required = true )
    private Integer isShare;
    @ApiModelProperty(value = "deleted，删除标记",required = true )
    private Integer deleted;
    @ApiModelProperty(value = "score，模型评分",required = false )
    private Float score;
    @ApiModelProperty(value = "fileId，接口文档Id",required = false )
    private String fileId;
    @ApiModelProperty(value = "callCount，调用次数",required = false )
    private Integer callCount;
    @ApiModelProperty(value = "minScore，最低分",required = false )
    private Float minScore;
    @ApiModelProperty(value = "maxScore，最高分",required = false )
    private Float maxScore;
    private String userName;
    private String dataMgrName;
    private Integer dataType;

    @Override
    public String toString() {
        return "StdModel{" +
                "stdModelId=" + stdModelId +
                ", stdModelType=" + stdModelType +
                ", stdModelName='" + stdModelName + '\'' +
                ", stdModelDes='" + stdModelDes + '\'' +
                ", dataMgrId=" + dataMgrId +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", isShare=" + isShare +
                ", deleted=" + deleted +
                ", score=" + score +
                ", fileId='" + fileId + '\'' +
                ", callCount=" + callCount +
                ", userName='" + userName + '\'' +
                ", dataMgrName='" + dataMgrName + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
