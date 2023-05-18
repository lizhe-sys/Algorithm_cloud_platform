package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.Permission;
import com.bh.sfapi.entity.shangfa.dto.PermissionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionDAO extends BaseDAO<Permission, String >{

    void addPermission(Permission permission);

    Permission getPermissionByUserId(Long userId);

    Permission getPermissionById(Long permissionId);

    void updatePermission(Permission permission);

    List<Permission> permissionList();

    List<Permission> dimQueryPermissionList(Permission permission);

    List<Permission> getPermissionNameList();

    Permission getPermissionByRoleName(String roleName);

    void deletePermission(Long permissionId);

}
