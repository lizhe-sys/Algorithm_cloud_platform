package com.bh.sfapi.entity.shangfa.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/23 22:51
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SynthesisInfoDto {

    @ApiModelProperty(value = "synthesisType，综合知识类型",required = false )
    private Integer synthesisType;
    @ApiModelProperty(value = "综合知识数量",required = true )
    private Integer synthesisCount;

    @Override
    public String toString() {
        return "SynthesisInfoDto{" +
                "synthesisType=" + synthesisType +
                ", synthesisCount=" + synthesisCount +
                '}';
    }
}
