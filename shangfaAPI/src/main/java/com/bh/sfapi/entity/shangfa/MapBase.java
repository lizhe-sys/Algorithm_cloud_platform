package com.bh.sfapi.entity.shangfa;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
public class MapBase {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "map_base_id，基线Id",required = false )
    private long map_base_id;
    @ApiModelProperty(value = "baseName",required = true )
    private String baseName;
    @ApiModelProperty(value = "baseFormula",required = true )
    private String baseFormula;
    @ApiModelProperty(value = "baseUnit",required = true )
    private String baseUnit;
    @ApiModelProperty(value = "mapMgrId",required = true )
    private long mapMgrId;
    @ApiModelProperty(value = "baseType",required = true )
    private Integer baseType;

    @Override
    public String toString() {
        return "MapBase{" +
                "map_base_id=" + map_base_id +
                ", baseName='" + baseName + '\'' +
                ", baseFormula='" + baseFormula + '\'' +
                ", baseUnit='" + baseUnit + '\'' +
                ", mapMgrId=" + mapMgrId +
                ", baseType=" + baseType +
                '}';
    }
}
