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
 * @create 2022/4/21 11:37
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Synthesis {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "综合知识编号",required = false )
    private Long synthesisId;
    @ApiModelProperty(value = "综合知识名称",required = true )
    private String synthesisName;
    @ApiModelProperty(value = "综合知识类型,1 文档知识；2 图片知识；3 视频知识",required = true )
    private int synthesisType;
    @ApiModelProperty(value = "创建用户",required = true )
    private long userId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "创建时间",required = false )
    private Date createTime;
    @ApiModelProperty(value = "使用频率",required = true )
    private int frequency;
    @ApiModelProperty(value = "删除标记",required = true )
    private int deleted;

    private String userName;

    @Override
    public String toString() {
        return "Systhesis{" +
                "synthesisId=" + synthesisId +
                ", synthesisName='" + synthesisName + '\'' +
                ", synthesisType=" + synthesisType +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", frequency='" + frequency + '\'' +
                ", deleted=" + deleted +
                ", userName='" + userName + '\'' +
                '}';
    }
}
