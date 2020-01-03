package com.bugs.service;

import com.bugs.entity.User;
import com.bugs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class AdminService {
    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> sendAdmin(String email, String password) {
        User user = userMapper.send(email, password);
        Map<String, Object> map = new HashMap<>();
        if(user!=null) {
            map.put("code", null);
            return map;
        }else {
            map.put("code", "xxx");
            return map;
        }
    }

    public Map<String,Object> insertAdmin(User user){
        Map<String, Object> map = new HashMap<>();
        String userId = user.getUserId();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        int permissions = user.getPermissions();
        String code = userMapper.selectPasswordByEmail(email);
        System.out.println(code+"-------");
        if(code==null) {
            userMapper.insertAdmin(userId, email, username, password, permissions);
            System.out.println("添加完成");
            map.put("code", null);
        }else {
            map.put("code", "email已存在");
        }
        return map;
    }
}
