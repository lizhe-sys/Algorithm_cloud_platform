package com.bh.sfapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2021/12/17 15:36
 * @desc
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors( chain = true )
public class QueryFields {

    private List<String> fields;

}
