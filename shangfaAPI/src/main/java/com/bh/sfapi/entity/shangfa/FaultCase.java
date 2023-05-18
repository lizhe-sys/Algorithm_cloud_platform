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
 * @create 2022/4/20 0:05
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FaultCase {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "faultCaseId，故障案例编号",required = false )
    private Long faultCaseId;
    @ApiModelProperty(value = "faultCaseName，故障案例名称",required = false )
    private String faultCaseName;
    @ApiModelProperty(value = "healthMgrId，健康管理编号",required = true )
    private Long healthMgrId;
    @ApiModelProperty(value = "mapMgrId，图谱管理编号",required = true )
    private Long mapMgrId;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
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
    @ApiModelProperty(value = "deleted，删除标记",required = false )
    private int deleted;
    @ApiModelProperty(value = "故障案例涉及的数据行数",required = false )
    private Double recordCount;

}
