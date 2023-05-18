package com.bh.sfapi.entity.shangfa;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    @ApiModelProperty(value = "permissionId，角色Id",required = false )
    private Long permissionId;
    @ApiModelProperty(value = "roleName，角色名称",required = true )
    private String roleName;
}
