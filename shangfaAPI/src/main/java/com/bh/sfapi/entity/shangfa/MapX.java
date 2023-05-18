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
 * @create 2022/4/7 19:44
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MapX {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "map_x_id，x轴Id",required = false )
    private long map_x_id;
    @ApiModelProperty(value = "xName",required = true )
    private String xName;
    @ApiModelProperty(value = "xFormula",required = true )
    private String xFormula;
    @ApiModelProperty(value = "xUnit",required = true )
    private String xUnit;
    @ApiModelProperty(value = "mapMgrId",required = true )
    private long mapMgrId;

    @Override
    public String toString() {
        return "MapX{" +
                "map_x_id=" + map_x_id +
                ", xName='" + xName + '\'' +
                ", xFormula='" + xFormula + '\'' +
                ", xUnit='" + xUnit + '\'' +
                ", mapMgrId=" + mapMgrId +
                '}';
    }
}
