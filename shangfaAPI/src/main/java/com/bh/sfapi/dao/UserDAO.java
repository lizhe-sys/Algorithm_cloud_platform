package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.Role;
import com.bh.sfapi.entity.shangfa.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDAO extends BaseDAO<User,Integer> {

    // 通过用户名进行查询
    List<User> getUserByUserName(String userName);

    // 通过用户Id查询用户
    User getUserByUserId(Long userId);

    // 修改用户信息
    void updateUser(User user);

    // 用户登录
    User login(@Param("userName") String userName, @Param( "password" ) String password );

    int getRoleCount(Long permissionId);

    String getNote(Long userid);

    void insert(String string,Long role);



    Long getcountPermission();

    void add(String string, Long role);

    List<Role> getRole();
}
