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
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/2/28 15:58
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MapMgr {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "mapMgrId，图谱编号",required = false )
    private Long mapMgrId;
    @ApiModelProperty(value = "图谱名称",required = true )
    private String mapName;
    @ApiModelProperty(value = "是否使用基线公式 1 使用；0 不使用。默认为0",required = true )
    private int useBase;
    @ApiModelProperty(value = "mapMgrId，模型id",required = false )
    private Long modelMgrId;
    @ApiModelProperty(value = "dataMgrId，数据id",required = false )
    private Long dataMgrId;
    @ApiModelProperty(value = "逻辑删除",required = true )
    private int deleted;
    @ApiModelProperty(value = "userId，用户Id",required = false )
    private Long userId;
    @ApiModelProperty(value = "mapType，图谱类型 1 数据图谱，2 模型图谱，3 概貌图",required = true )
    private int mapType;
    @ApiModelProperty(value = "模型内图谱文件名",required = false )
    private String modelMapName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "数据模块创建时间",required = true )
    private Date createTime;

    private String dataMgrName;
    private String modelName;
    private List<MapX> xaxis;
    private List<MapY> yaxis;

    private List<MapBase> baseLine;
    @Override
    public String toString() {
        return "MapMgr{" +
                "mapMgrId=" + mapMgrId +
                ", mapName='" + mapName + '\'' +
                ", useBase=" + useBase +
                ", modelMgrId=" + modelMgrId +
                ", dataMgrId=" + dataMgrId +
                ", deleted=" + deleted +
                ", userId=" + userId +
                ", mapType=" + mapType +
                ", modelMapName='" + modelMapName + '\'' +
                ", createTime=" + createTime +
                ", dataMgrName='" + dataMgrName + '\'' +
                ", modelName='" + modelName + '\'' +

                '}';
    }
}
