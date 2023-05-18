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
 * @create 2022/2/21 16:21
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HealthMgr {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long OlddataMgrId;
    @ApiModelProperty(value = "healthMgrId，健康管理Id",required = true )
    private Long healthMgrId;
    @ApiModelProperty(value = "healthMgrName，健康管理名称",required = true )
    private String healthMgrName;
    @ApiModelProperty(value = "dataMgrId，数据模块Id",required = true )
    private Long dataMgrId;
    @ApiModelProperty(value = "modelId，模型编号",required = true )
    private Long modelMgrId;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "健康创建时间",required = true )
    private Date createTime;
    @ApiModelProperty(value = "deleted，删除标记",required = true )
    private Integer deleted;
    private String startTime;
    @Override
    public String toString() {
        return "HealthMgr{" +
                "healthMgrId=" + healthMgrId +
                ", healthMgrName='" + healthMgrName + '\'' +
                ", dataMgrId=" + dataMgrId +
                ", modelMgrId=" + modelMgrId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                ", startTime=" + startTime +
                '}';
    }
}
