package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.UserDAO;
import com.bh.sfapi.entity.DockerContainer;
import com.bh.sfapi.entity.shangfa.Role;
import com.bh.sfapi.entity.shangfa.User;
import com.bh.sfapi.service.DockerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/11 16:46
 * @desc
 */

@Service
@Transactional
public class UserServiceImpl implements UserServiceApi {

    @Autowired
    UserDAO userDAO;
    @Autowired
    DockerApi dockerApi;

    // 远程连接ip
    @Value("${remoteIp}")
    private String remoteIp;
    // 远程连接用户名
    @Value("${remoteName}")
    private String remoteName;
    // 远程连接密码
    @Value("${remotePwd}")
    private String remotePwd;

    @Override
    public void save(User user) {
        userDAO.save( user );
    }

    @Override
    public List<User> getUserByUserName(String userName) {
        List<DockerContainer> containers = dockerApi.containerList( remoteIp , remoteName , remotePwd );
        List<User> users = userDAO.getUserByUserName(userName);
        Map<String , String > containerMap = new HashMap<>();
        if( containers != null ){
            for (int i = 0; i < containers.size(); i++) {
                DockerContainer dockerContainer = containers.get(i);
                containerMap.put( dockerContainer.getName() , dockerContainer.getStatu() );
            }
        }
        List<User> res = new ArrayList<>();
        if( users != null ){
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                String key = "jupyter-" + user.getUserId();
                String value = containerMap.getOrDefault(key, null);
                user.setContainerStatu( value );
                res.add( user );
            }
        }
        return res;
    }

    @Override
    public User getUserByUserId(Long userId) {
        User user = userDAO.getUserByUserId(userId);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser( user );
    }

    @Override
    public List<User> getAllUser() {
        List<DockerContainer> containers = dockerApi.containerList( remoteIp , remoteName , remotePwd );
        List<User> users = userDAO.findAll();
        Map<String , String > containerMap = new HashMap<>();
        if( containers != null ){
            for (int i = 0; i < containers.size(); i++) {
                DockerContainer dockerContainer = containers.get(i);
                containerMap.put( dockerContainer.getName() , dockerContainer.getStatu() );
            }
        }
        List<User> res = new ArrayList<>();
        if( users != null ){
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                String key = "jupyter-" + user.getUserId();
                String value = containerMap.getOrDefault(key, null);
                user.setContainerStatu( value );
                res.add( user );
            }
        }
        return res;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User login(String userName, String password) {
        return userDAO.login( userName , password );
    }

    @Override
    public int getRoleCount(Long permissionId) {
        return userDAO.getRoleCount( permissionId );
    }

    @Override
    public String getNote(Long userid) {
        return userDAO.getNote(userid);
    }

    @Override
    public void insert(String string , Long role) {
        userDAO.insert(string,role);
    }



    @Override
    public Long getcountPermission() {
        return userDAO.getcountPermission();
    }

    @Override
    public void add(String string, Long role) {
        userDAO.add(string,role);
    }

    @Override
    public List<Role> getRole() {
        return userDAO.getRole();
    }


}
