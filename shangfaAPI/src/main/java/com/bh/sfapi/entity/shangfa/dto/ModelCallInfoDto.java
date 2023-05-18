package com.bh.sfapi.entity.shangfa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/23 23:24
 * @desc
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelCallInfoDto {

    @ApiModelProperty(value = "modelMgrId，模型Id",required = false )
    private Long modelMgrId;
    @ApiModelProperty(value = "模型调用数量",required = true )
    private Long modelCallCount;
}
