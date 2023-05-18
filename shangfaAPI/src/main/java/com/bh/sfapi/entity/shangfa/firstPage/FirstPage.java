package com.bh.sfapi.entity.shangfa.firstPage;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/21 20:37
 * @desc
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FirstPage {

    @ApiModelProperty(value = "id，首页记录编号",required = false )
    private Long id;
    @ApiModelProperty(value = "dataMgr_data，试验数据数量",required = true )
    private Integer dataMgr_data;
    @ApiModelProperty(value = "dataMgr_data_size，试验数据数据量，单位：G",required = true )
    private Float dataMgr_data_size;
    @ApiModelProperty(value = "dataMgr_data，试验数据总时长，单位：小时",required = true )
    private Float dataMgr_data_time;
    @ApiModelProperty(value = "dataMgr_video，视频数据数量",required = true )
    private Integer dataMgr_video;
    @ApiModelProperty(value = "dataMgr_video，图片数据数量",required = true )
    private Integer dataMgr_picture;
    @ApiModelProperty(value = "dataMgr_video，其她数据数据数量",required = true )
    private Integer dataMgr_other;
    @ApiModelProperty(value = "dataMgr_video，在线模型数量",required = true )
    private Integer stdModel_online;
    @ApiModelProperty(value = "dataMgr_video，封装模型数量",required = true )
    private Integer stdModel_package;
    @ApiModelProperty(value = "dataMgr_video，文档知识数量",required = true )
    private Integer knowledge_file;
    @ApiModelProperty(value = "dataMgr_video，视频知识数量",required = true )
    private Integer knowledge_video;
    @ApiModelProperty(value = "dataMgr_video，图片知识数量",required = true )
    private Integer knowledge_picture;
    @ApiModelProperty(value = "knowledge_faultcase，故障案例数量",required = true )
    private Integer knowledge_faultcase;
    @ApiModelProperty(value = "map_data，数据图谱数量",required = true )
    private Integer map_data;
    @ApiModelProperty(value = "map_model，模型图谱数量",required = true )
    private Integer map_model;
    @ApiModelProperty(value = "health_count，试验工程数量",required = true )
    private Integer health_count;

}
