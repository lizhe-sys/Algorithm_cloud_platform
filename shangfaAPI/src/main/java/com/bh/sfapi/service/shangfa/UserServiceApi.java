package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.entity.shangfa.Role;
import com.bh.sfapi.entity.shangfa.User;

import java.util.List;

public interface UserServiceApi {

    public void save( User user );

    public List<User> getUserByUserName(String userName );

    public User getUserByUserId(Long userId);

    public void updateUser(User user);

    public List<User> getAllUser();

    public List<User> getUsers();

    User login(String userName, String password);

    int getRoleCount(Long permissionId);

    String getNote(Long userId);

    void insert(String string,Long role);



    Long getcountPermission();

    void add(String string, Long role);

    List<Role> getRole();
}
