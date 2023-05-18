package com.bh.sfapi.entity.shangfa.dto;

import com.bh.sfapi.entity.shangfa.MapBase;
import com.bh.sfapi.entity.shangfa.MapX;
import com.bh.sfapi.entity.shangfa.MapY;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/7 19:43
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MapDto {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "mapMgrId，图谱编号",required = false )
    private Long mapMgrId;
    @ApiModelProperty(value = "图谱名称",required = true )
    private String mapName;
    @ApiModelProperty(value = "x轴列表",required = true )
    private List<MapX> xAxis;
    @ApiModelProperty(value = "y轴列表",required = true )
    private List<MapY> yAxis;
    @ApiModelProperty(value = "基线列表",required = true )
    private List<MapBase> baseLine;
    @ApiModelProperty(value = "是否使用基线公式 1 使用；0 不使用。默认为0",required = true )
    private int useBase;
    @ApiModelProperty(value = "mapMgrId，模型id",required = true )
    private Long modelMgrId;
    @ApiModelProperty(value = "dataMgrId，数据id",required = false )
    private Long dataMgrId;
    @ApiModelProperty(value = "逻辑删除",required = true )
    private int deleted;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "mapType，图谱类型 1 概貌图，2 模型图谱，3 数据图谱",required = true )
    private int mapType;
    @ApiModelProperty(value = "模型内图谱文件名",required = false )
    private String modelMapName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "数据模块创建时间",required = true )
    private Date createTime;

    private String userName;
    private String modelName;
    private String dataMgrName;
    private Long typeId;
    @ApiModelProperty(value = "mapTypes，图谱显示类型 0 2x3，1 2x2，2 1x1",required = true )
    private Long mapTypes;


}
