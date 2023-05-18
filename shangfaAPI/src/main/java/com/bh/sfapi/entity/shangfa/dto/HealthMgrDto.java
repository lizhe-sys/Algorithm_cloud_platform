package com.bh.sfapi.entity.shangfa.dto;

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
public class HealthMgrDto {

    @JsonSerialize(using = ToStringSerializer.class)

    @ApiModelProperty(value = "healthMgrId，健康管理Id",required = true )
    private Long healthMgrId;
    @ApiModelProperty(value = "healthMgrName，健康管理名称",required = true )
    private String healthMgrName;
    @ApiModelProperty(value = "inflxudb数据库 对应发动机型号，例：EMUGroundData",required = true )
    private String inflxudbDatabase;
    @ApiModelProperty(value = "influxdb数据表 对应试验台位，例：groundCheckData1",required = true )
    private String inflxudbMeasurement;
    @ApiModelProperty(value = "对应实验科目 例：C1A01",required = true )
    private String motorNo;
    @ApiModelProperty(value = "实验名称",required = false )
    private String dataMgrName;
    @ApiModelProperty(value = "dataMgrId，实验编号",required = true )
    private Long dataMgrId;
    @ApiModelProperty(value = "modelName，模型名称",required = true )
    private String modelName;
    @ApiModelProperty(value = "modelMgrId，模型编号",required = true )
    private Long modelMgrId;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "创建人",required = true )
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "健康创建时间",required = true )
    private Date createTime;
    private String startTime;


    @Override
    public String toString() {
        return "HealthMgrDto{" +
                "healthMgrId=" + healthMgrId +
                ", healthMgrName='" + healthMgrName + '\'' +
                ", inflxudbDatabase='" + inflxudbDatabase + '\'' +
                ", inflxudbMeasurement='" + inflxudbMeasurement + '\'' +
                ", motorNo='" + motorNo + '\'' +
                ", dataMgrName='" + dataMgrName + '\'' +
                ", dataMgrId=" + dataMgrId +
                ", modelName='" + modelName + '\'' +
                ", modelMgrId=" + modelMgrId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
