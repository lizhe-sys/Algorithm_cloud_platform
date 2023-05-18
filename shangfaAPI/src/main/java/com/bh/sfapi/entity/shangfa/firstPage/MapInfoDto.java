package com.bh.sfapi.entity.shangfa.firstPage;

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
 * @create 2022/4/23 22:16
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MapInfoDto {

    @ApiModelProperty(value = "mapType，图谱类型",required = false )
    private int mapType;
    @ApiModelProperty(value = "图谱数量",required = true )
    private int mapCount;

    @Override
    public String toString() {
        return "MapInfoDto{" +
                "mapType=" + mapType +
                ", mapCount=" + mapCount +
                '}';
    }
}
