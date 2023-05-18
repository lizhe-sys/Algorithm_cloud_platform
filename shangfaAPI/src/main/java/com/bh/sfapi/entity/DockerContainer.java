package com.bh.sfapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2021/12/10 14:17
 * @desc
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DockerContainer {

    private String name;
    private String created;
    private String statu;
}
