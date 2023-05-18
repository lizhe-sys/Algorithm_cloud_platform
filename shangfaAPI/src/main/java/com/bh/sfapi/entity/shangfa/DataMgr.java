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
 * @create 2022/1/12 14:00
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataMgr {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "dataMgrId，数据工程编号",required = false )
    private Long dataMgrId;
    @ApiModelProperty(value = "motorType，发动机型号",required = false )
    private String motorType;
    @ApiModelProperty(value = "dataType，数据类型",required = true )
    private Integer dataType;
    @ApiModelProperty(value = "inflxudb数据库，例：EMUGroundData",required = true )
    private String inflxudbDatabase;
    @ApiModelProperty(value = "influxdb数据表，例：groundCheckData1",required = true )
    private String inflxudbMeasurement;
    @ApiModelProperty(value = "例：C1A01",required = true )
    private String motorNo;
    @ApiModelProperty(value = "第二层数据表名",required = false )
    private String secondMeasurement;
    @ApiModelProperty(value = "实验名称",required = false )
    private String dataMgrName;
    @ApiModelProperty(value = "实验描述",required = false )
    private String dataMgrDescription;
    @ApiModelProperty(value = "附件存储路径",required = false )
    private String filePath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "数据开始时间",required = true )
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "数据结束时间",required = true )
    private Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "数据模块创建时间",required = true )
    private Date createTime;
    @ApiModelProperty(value = "用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "写入完成",required = false )
    private Integer finished = 0;
    @ApiModelProperty(value = "是否删除",required = false )
    private Integer deleted = 0;

    @ApiModelProperty(value = "与该数据工程关联的数据图谱数量",required = true )
    private Integer dataMapCount = 0;

    private String userName;
    private String firstField;
    private String fileId;


}
