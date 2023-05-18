package com.bh.sfapi.entity.shangfa.firstPage;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/22 1:21
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FaultCaseRecordCount {

    private String faultCaseName;
    private Integer recordCount;

}
