package com.bh.sfapi.entity.shangfa;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlyEye {
    @ApiModelProperty(value = "FlyId，飞机之眼Id",required = false )
    private Long FlyId;
    @ApiModelProperty(value = "healthMgrId，健康管理Id",required = false )
    private Long healthMgrId;
    @ApiModelProperty(value = "Name1，字段1",required = true )
    private String Name1;
    @ApiModelProperty(value = "Name2，字段2",required = true )
    private String Name2;
    @ApiModelProperty(value = "Name3，字段3",required = true )
    private String Name3;
    @ApiModelProperty(value = "Name4，字段4",required = true )
    private String Name4;
    @ApiModelProperty(value = "Name5，字段5",required = true )
    private String Name5;
    @ApiModelProperty(value = "Name6，字段6",required = true )
    private String Name6;
    @ApiModelProperty(value = "Name7，字段7",required = true )
    private String Name7;
    @ApiModelProperty(value = "Name8，字段8",required = true )
    private String Name8;
    @ApiModelProperty(value = "Name9，字段9",required = true )
    private String Name9;
    @ApiModelProperty(value = "Name10，字段10",required = true )
    private String Name10;
    @ApiModelProperty(value = "Name11，字段11",required = true )
    private String Name11;
    @ApiModelProperty(value = "Name12，字段12",required = true )
    private String Name12;
    @ApiModelProperty(value = "Name13，字段13",required = true )
    private String Name13;
    @ApiModelProperty(value = "Name14，字段14",required = true )
    private String Name14;
    @ApiModelProperty(value = "Name15，字段15",required = true )
    private String Name15;
    @ApiModelProperty(value = "Name16，字段16",required = true )
    private String Name16;
    @ApiModelProperty(value = "Name17，字段17",required = true )
    private String Name17;
    @ApiModelProperty(value = "Name18，字段18",required = true )
    private String Name18;
    @ApiModelProperty(value = "Name19，字段19",required = true )
    private String Name19;
    private Long Name1_max;
    private Long Name1_min;
    private Long Name2_max;
    private Long Name2_min;
    private Long Name3_max;
    private Long Name3_min;
    private Long Name4_max;
    private Long Name4_min;
    private Long Name5_max;
    private Long Name5_min;
    private Long Name6_max;
    private Long Name6_min;

}
