package com.bh.sfapi.entity.shangfa;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/8 21:31
 * @desc
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Permission {

    @ApiModelProperty(value = "permissionId，角色Id",required = false )
    private Long permissionId;
    @ApiModelProperty(value = "roleName，角色名称",required = true )
    private String roleName;
    // 以4位二进制代表 增删改查四种操作的权限 例如1111->15 代表该用户具备增删改查四种权限
    @ApiModelProperty(value = "permission_dataMgr，数据模块权限",required = false )
    private int permission_dataMgr;
    // 以5位二进制代表 增删改查调试五种操作权限
    @ApiModelProperty(value = "permission_stdModel，标准模型权限",required = false )
    private int permission_stdModel;
    // 以5位二进制代表 增删改查调试五种操作权限
    @ApiModelProperty(value = "permission_modelMgr，模型应用权限",required = true )
    private int permission_modelMgr;
    @ApiModelProperty(value = "permission_map，图谱权限",required = true )
    private int permission_map;
    @ApiModelProperty(value = "permission_health，健康权限",required = true )
    private int permission_health;
    @ApiModelProperty(value = "permission_faultCase，故障案例权限",required = true )
    private int permission_faultCase;
    @ApiModelProperty(value = "permission_synthesis，综合知识权限",required = true )
    private int permission_synthesis;
    @ApiModelProperty(value = "permission_type，权限类型：0：永久权限；1：临时权限",required = true )
    private int permission_type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "permission_startTime，临时权限的起始时间",required = true )
    private Date permission_startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "permission_endTime，临时权限的结束时间",required = true )
    private Date permission_endTime;
    @ApiModelProperty(value = "deleted，删除标记",required = true )
    private Integer deleted;

    // 用户权限是否失效 0：代表未失效；1：代表已失效，失效情况出现在用户的临时权限过期
    private int invalidation = 0;

    private String userName;

    private String sname_Chinese;
    private String sname_English;


}
