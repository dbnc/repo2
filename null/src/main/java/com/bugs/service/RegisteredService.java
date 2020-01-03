package com.bugs.service;

import com.bugs.entity.User;
import com.bugs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RegisteredService {
    @Autowired
    private UserMapper userMapper;

    public Map<String,Object> registered(String email,String password, String password1){
        HashMap<String, Object> map = new HashMap<>();
        User user1 = userMapper.selectByEmail(email);
        if(user1==null){
            if(password.equals(password1)){
                String userId = UUID.randomUUID().toString();
                String username = "null";
                User user = new User(userId, email, username, password, 1);
                System.out.println("User user = new  "+user);
                userMapper.insertUser(userId, email, username, password, 1);
                System.out.println("用户email:"+email+"注册成功");
            }else {
                map.put("encode", "两次的密码不一致");
            }
        }else{
            map.put("encode","email已存在");
        }
        return map;
    }
}
