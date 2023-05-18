package com.bh.sfapi.entity.shangfa.firstPage;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/25 12:13
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataMgrInfo {

    @ApiModelProperty(value = "dataMgr_data，试验数据数量",required = true )
    private Integer dataMgr_data;
    @ApiModelProperty(value = "dataMgr_data，试验数据总时长，单位：小时",required = true )
    private Float dataMgr_data_time;
    @ApiModelProperty(value = "dataMgr_data_size，试验数据数据量，单位：G",required = true )
    private Float dataMgr_data_size;

}
