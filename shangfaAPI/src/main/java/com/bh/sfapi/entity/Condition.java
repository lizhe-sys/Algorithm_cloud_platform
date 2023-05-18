package com.bh.sfapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author zheli
 * @version 1.0
 * @date 2021/11/29 16:45
 * @desc
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Condition {
    private String querySql;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
}
