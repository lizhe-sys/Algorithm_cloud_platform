package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.entity.shangfa.Permission;
import com.bh.sfapi.entity.shangfa.dto.PermissionDto;

import java.util.List;

public interface PermissionService {

    void addPermission(Permission permission);

    Permission getPermissionByUserId(Long userId);

    Permission getPermissionById(Long permissionId);

    void updatePermission(Permission permission);

    List<PermissionDto> getPermissionList();

    List<PermissionDto> dimQueryPermissionList(Permission permission);

    int getUserOperatePermission(String field, int operate, Long userId);

    List<Permission> getPermissionNameList();

    Permission getPermissionByRoleName( String roleName );

    void deletePermission(Long permissionId);

}
