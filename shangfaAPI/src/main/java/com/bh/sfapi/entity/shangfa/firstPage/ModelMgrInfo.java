package com.bh.sfapi.entity.shangfa.firstPage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/21 23:20
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelMgrInfo {
    @ApiModelProperty(value = "模型工程名称",required = true )
    private String modelName;
    @ApiModelProperty(value = "模型工程类型，1：在线模型，2：封装模型",required = true )
    private Integer type;
    @ApiModelProperty(value = "模型工程创建者",required = true )
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "模型工程创建时间",required = true )
    private Date createTime;
}
