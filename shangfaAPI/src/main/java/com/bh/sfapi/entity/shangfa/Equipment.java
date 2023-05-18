package com.bh.sfapi.entity.shangfa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/19 21:47
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Equipment {

    private String equipmentUuid;
    private String groupName;
    private String companyName;
    private String factoryName;
    private String unitName;
    private String equipmentTypeName;
    private String equipmentName;
    private String driveName;
    private Integer alarmStatus;
}
