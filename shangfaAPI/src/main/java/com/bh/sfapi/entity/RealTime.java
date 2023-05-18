package com.bh.sfapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @create 2021/12/15 15:42
 * @desc
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors( chain = true )
public class RealTime {

    private Integer id;
    private Date time;
    private String name;
    private float value;
    private String influxdb_database;

}
