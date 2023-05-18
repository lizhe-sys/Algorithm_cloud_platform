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
public class MapY {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "map_y_id，y轴Id",required = false )
    private long map_y_id;
    @ApiModelProperty(value = "yName",required = true )
    private String yName;
    @ApiModelProperty(value = "yFormula",required = true )
    private String yFormula;
    @ApiModelProperty(value = "yUnit",required = true )
    private String yUnit;
    @ApiModelProperty(value = "mapMgrId",required = true )
    private long mapMgrId;

    @Override
    public String toString() {
        return "MapY{" +
                "map_y_id=" + map_y_id +
                ", yName='" + yName + '\'' +
                ", yFormula='" + yFormula + '\'' +
                ", yUnit='" + yUnit + '\'' +
                ", mapMgrId=" + mapMgrId +
                '}';
    }
}
