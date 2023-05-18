package com.bh.sfapi.entity.shangfa;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/11 16:33
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @ApiModelProperty(value = "用户Id",required = false)
    private Long userId;
    @ApiModelProperty(value = "用户名",required = true )
    private String userName;
    @ApiModelProperty(value = "密码",required = true )
    private String password;
    @ApiModelProperty(value = "电话号码",required = true )
    private String tel;
    @ApiModelProperty(value = "邮箱地址",required = true )
    private String email;
    @ApiModelProperty(value = "用户角色",required = true )
    private Long role;
    @ApiModelProperty(value = "记录是否删除 0未删除；1已删除",required = true )
    private Integer deleted;
    @ApiModelProperty(value = "fileName，头像文件存储路径",required = true )
    private String fileName;
    @ApiModelProperty(value = "skinType，皮肤种类：0 蓝色；1 白色",required = true )
    private Integer skinType;

    @ApiModelProperty(value = "containerAllocate，是否分配容器：0 未分配；1 已分配",required = true )
    private Integer containerAllocate;
    @ApiModelProperty(value = "containerMemory，容器内存大小",required = true )
    private Integer containerMemory;
    @ApiModelProperty(value = "containerCpu，容器cpu个数",required = true )
    private Integer containerCpu;
    @ApiModelProperty(value = "containerStatu，容器状态",required = true )
    private String containerStatu;

    private String roleName;
    private String sname_Chinese;
    private String sname_English;

}
