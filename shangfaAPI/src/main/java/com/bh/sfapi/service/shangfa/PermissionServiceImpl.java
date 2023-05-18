package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.dao.PermissionDAO;
import com.bh.sfapi.entity.shangfa.Permission;
import com.bh.sfapi.entity.shangfa.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDAO permissionDAO;

    @Override
    public void addPermission(Permission permission) {

        if( permission.getPermission_type() == 1 ){
            permission.setPermission_startTime( new Date() );
        }
        permissionDAO.addPermission( permission );
    }

    @Override
    public Permission getPermissionByUserId(Long userId) {
        return permissionDAO.getPermissionByUserId( userId );
    }

    @Override
    public Permission getPermissionById(Long permissionId) {
        return permissionDAO.getPermissionById( permissionId );
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionDAO.updatePermission( permission );
    }

    @Override
    public List<PermissionDto> getPermissionList() {
        List<Permission> permissions = permissionDAO.permissionList();
        List<PermissionDto> res = new ArrayList<>();
        res = getRes( permissions );
        return res;
    }

    @Override
    public List<PermissionDto> dimQueryPermissionList(Permission permission) {
        List<Permission> permissions = permissionDAO.dimQueryPermissionList(permission);
        List<PermissionDto> res = new ArrayList<>();
        res = getRes( permissions );
        return res;
    }

    @Override
    public int getUserOperatePermission(String field, int operate, Long userId) {

        Permission permission= permissionDAO.getPermissionByUserId(userId);

        // 首先检查是否为临时权限
        // 如果是临时权限，判断临时权限是否过期
        if( permission.getPermission_type() == 1 ){
            Date endTime = permission.getPermission_endTime();
            if( endTime.compareTo( new Date( ) ) == -1 ){
                return 0;
            }
        }
        int value  =0;
        if( "dataMgr".equals( field ) )
            value =  permission.getPermission_dataMgr();
        else if( "stdModel".equals( field ) )
            value =  permission.getPermission_stdModel();
        else if( "modelMgr".equals( field ) )
            value =  permission.getPermission_modelMgr();
        else if( "map".equals( field ) )
            value =  permission.getPermission_map();
        else if( "health".equals( field ) )
            value =  permission.getPermission_health();
        else if( "faultCase".equals( field ) )
            value =  permission.getPermission_faultCase();
        else if( "synthesis".equals( field ) )
            value =  permission.getPermission_synthesis();

        int num = 1 << operate;
        return ( value & num  ) == 0 ? 0 : 1;
    }

    @Override
    public List<Permission> getPermissionNameList() {
        return permissionDAO.getPermissionNameList();
    }

    @Override
    public Permission getPermissionByRoleName( String roleName ) {
        return permissionDAO.getPermissionByRoleName( roleName );
    }

    @Override
    public void deletePermission(Long permissionId) {
        System.out.println( permissionId );
        permissionDAO.deletePermission( permissionId );
    }

    public List<PermissionDto> getRes( List<Permission> permissions ){
        List<PermissionDto> res = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            PermissionDto permissionDto = new PermissionDto();

            permissionDto.setPermissionId( permission.getPermissionId() );
            permissionDto.setRoleName( permission.getRoleName() );
            permissionDto.setUserName( permission.getUserName() );
            permissionDto.setPermission_type( permission.getPermission_type() );
            permissionDto.setPermission_startTime( permission.getPermission_startTime() );
            permissionDto.setPermission_endTime( permission.getPermission_endTime() );
            permissionDto.setSname_Chinese(permission.getSname_Chinese());
            permissionDto.setSname_English(permission.getSname_English());
            // 如果是临时权限，判断临时权限是否过期
            if( permission.getPermission_type() == 1 ){
                Date endTime = permission.getPermission_endTime();
                if( endTime.compareTo( new Date( ) ) == -1 ){
                    permissionDto.setInvalidation( 1 );
                }
            }

            // 将对应的权限数值 转化为权限列表
            permissionDto.setPermissionDataMgr( changeToPermissionList(permission.getPermission_dataMgr(), 0) );
            permissionDto.setPermissionStdModel( changeToPermissionList(permission.getPermission_stdModel(), 1) );
            permissionDto.setPermissionModelMgr( changeToPermissionList(permission.getPermission_modelMgr(), 1) );
            permissionDto.setPermissionMap( changeToPermissionList(permission.getPermission_map(), 0) );
            permissionDto.setPermissionHealth( changeToPermissionList(permission.getPermission_health(), 0) );
            permissionDto.setPermissionFaultCase( changeToPermissionList(permission.getPermission_faultCase(), 0) );
            permissionDto.setPermissionSynthesis( changeToPermissionList(permission.getPermission_synthesis(), 0) );

            res.add( permissionDto );
        }
        return res;
    }

    public List changeToPermissionList( int value ,  int type ){
        List<Integer> res = new ArrayList();
        // 增删改查
        if ((value & 1) == 0) {
            res.add(0);
        } else {
            res.add(1);
        }
        if ((value & 2) == 0) {
            res.add(0);
        } else {
            res.add(1);
        }
        if ((value & 4) == 0) {
            res.add(0);
        } else {
            res.add(1);
        }
        if ((value & 8) == 0) {
            res.add(0);
        } else {
            res.add(1);
        }
        // 增删改查调试
        if( type == 1 ){
            if ((value & 16) == 0) {
                res.add(0);
            } else {
                res.add(1);
            }
        }
        return res;
    }
}
