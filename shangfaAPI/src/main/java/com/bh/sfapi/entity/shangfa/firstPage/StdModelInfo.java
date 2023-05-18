package com.bh.sfapi.entity.shangfa.firstPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/21 22:35
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StdModelInfo {

    private Float avgScore;
    private Float minScore;
    private Float maxScore;

    private Integer callCount;
    private String stdModelName;

    // 月份
    private Integer month;
    // 数量
    private Integer num;
}
