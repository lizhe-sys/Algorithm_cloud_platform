package com.bh.sfapi.entity.shangfa;

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
 * @create 2022/5/1 22:52
 * @desc
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelScore {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "modelScoreId，模型评分Id",required = true )
    private Long modelScoreId;
    @ApiModelProperty(value = "stdModelMgrId，标准模型编号",required = true )
    private Long stdModelId;
    @ApiModelProperty(value = "usreId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "score，评分",required = true )
    private float score;

    private String userName;

    @Override
    public String toString() {
        return "ModelScore{" +
                "modelScoreId=" + modelScoreId +
                ", stdModelId=" + stdModelId +
                ", userId=" + userId +
                ", score=" + score +
                '}';
    }
}
