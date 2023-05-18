package com.bh.sfapi.entity.shangfa.firstPage;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/25 12:09
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MotorInfo {

    @ApiModelProperty(value = "motorType，发动机类型",required = false )
    private String motorType;
    @ApiModelProperty(value = "subjectType，试验科目",required = false )
    private String subjectType;
    @ApiModelProperty(value = "motorNo，试验台位",required = false )
    private String motorNo;



}
