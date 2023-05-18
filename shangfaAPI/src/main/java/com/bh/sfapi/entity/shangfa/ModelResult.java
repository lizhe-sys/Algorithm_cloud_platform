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
 * @create 2022/1/21 15:21
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

/**
 * 模型输出结果
 */
public class ModelResult {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "modelResultId，输出结果Id",required = false )
    private Long modelResultId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    @ApiModelProperty(value = "横坐标为time",required = true )
    private Date xtime;
    @ApiModelProperty(value = "横坐标为float",required = true )
    private Float xfloat;
    @ApiModelProperty(value = "纵坐标值",required = true )
    private Float yvalue;

}
