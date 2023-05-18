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
 * @create 2022/4/20 0:10
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FaultCaseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "faultCaseId，故障案例编号",required = false )
    private Long faultCaseId;
    @ApiModelProperty(value = "faultCaseName，故障案例名称",required = false )
    private String faultCaseName;
    @ApiModelProperty(value = "healthMgrId，健康管理Id",required = true )
    private Long healthMgrId;
    @ApiModelProperty(value = "mapMgrId，图谱Id",required = true )
    private Long mapMgrId;

    @ApiModelProperty(value = "healthMgrName：健康名称",required = false )
    private String healthMgrName;
    @ApiModelProperty(value = "dataMgrName：实验名称",required = false )
    private String dataMgrName;
    @ApiModelProperty(value = "dataMgrId，实验编号",required = true )
    private Long dataMgrId;

    @ApiModelProperty(value = "modelMgrId，模型编号",required = true )
    private Long modelMgrId;
    @ApiModelProperty(value = "modelName，模型名称",required = true )
    private String modelName;

    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "创建人",required = true )
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "数据开始时间",required = true )
    private String faultStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "数据结束时间",required = true )
    private String faultEndTime;
    @ApiModelProperty(value = "faultDescription，故障案例描述",required = true )
    private String faultDescription;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "故障案例创建时间",required = true )
    private Date createTime;

}




