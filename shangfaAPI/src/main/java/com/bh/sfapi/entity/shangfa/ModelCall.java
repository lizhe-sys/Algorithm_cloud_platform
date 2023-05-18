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
 * @create 2022/1/19 16:37
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelCall {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "modelCallId，模型调用记录编号",required = false )
    private Long modelCallId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "callTime，模型调用时间",required = true )
    private Date callTime;
    @ApiModelProperty(value = "stdModelId，标准模型编号",required = true )
    private Long stdModelId;
    @ApiModelProperty(value = "modelMgrId，模型编号",required = true )
    private Long modelMgrId;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "isTrue，调用结果是否正确",required = true )
    private Integer isTrue;
    @ApiModelProperty(value = "deleted，删除标记",required = true )
    private Integer deleted;

    private String userName;
    private String stdModelName;
    private String modelName;

}
