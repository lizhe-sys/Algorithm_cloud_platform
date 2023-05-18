package com.vip.file.model.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/31 18:42
 * @desc
 */

@Data
@ToString
@Accessors(chain = true)
public class FieldCofig {

    private List<String> fieldLsit;

}
