package com.bugs.service;

import com.bugs.entity.User;
import com.bugs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> sendByPage(Integer pg) {
        Integer startNum = (pg-1)*20;
        List<User> users = userMapper.sendByPage(startNum);
//        System.out.println("user列表内容            "+users);
        return users;
    }

    public List<User> sendAdminByPage(Integer pg) {
        Integer startNum = (pg-1)*20;
        List<User> admins = userMapper.sendAdminByPage(startNum);
        return admins;
    }

    public void updateUser(String oldEmail,User user){
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        int permissions = user.getPermissions();
        System.out.println(email+" "+username+" "+permissions);
        userMapper.updateUser(oldEmail,email,username,password,permissions);

    }

    public void deleteUser(String email){
        userMapper.deleteUser(email);
        System.out.println("email=="+email+"删除成功");
    }

    public void updateAdmin(User user){
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        int permissions = user.getPermissions();
        userMapper.updateAdmin(email,username,password,permissions);
    }

}
