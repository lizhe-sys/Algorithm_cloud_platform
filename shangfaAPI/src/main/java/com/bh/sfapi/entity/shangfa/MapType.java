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
 * @create 2022/4/8 15:18
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MapType {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "typeId，样式编号",required = false )
    private Long typeId;
    @ApiModelProperty(value = "map_1，图谱1",required = false )
    private Long map_1;
    @ApiModelProperty(value = "map_2，图谱2",required = false )
    private Long map_2;
    @ApiModelProperty(value = "map_3，图谱3",required = false )
    private Long map_3;
    @ApiModelProperty(value = "map_4，图谱4",required = false )
    private Long map_4;
    @ApiModelProperty(value = "map_5，图谱5",required = false )
    private Long map_5;
    @ApiModelProperty(value = "map_6，图谱6",required = false )
    private Long map_6;
    @ApiModelProperty(value = "healthMgrId，健康Id",required = false )
    private Long healthMgrId;
    @ApiModelProperty(value = "page，页号",required = false )
    private Long page;
    //0为2*3，1为2*2，2为1*1
    @ApiModelProperty(value = "mapType，页面类型",required = false )
    private Long mapType;


    @Override
    public String toString() {
        return "MapType{" +
                "typeId=" + typeId +
                ", map_1=" + map_1 +
                ", map_2=" + map_2 +
                ", map_3=" + map_3 +
                ", map_4=" + map_4 +
                ", map_5=" + map_5 +
                ", map_6=" + map_6 +
                ", healthMgrId=" + healthMgrId +
                ", page=" + page +
                ", mapType=" + mapType +
                '}';
    }
}
