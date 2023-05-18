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
public class KnowledgeMgrDto {

    @JsonSerialize(using = ToStringSerializer.class)

    @ApiModelProperty(value = "knowledgeMgrId，知识Id",required = true )
    private Long knowledgeMgrId;

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
    @ApiModelProperty(value = "知识创建人",required = true )
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "知识创建时间",required = true )
    private Date createTime;


}
