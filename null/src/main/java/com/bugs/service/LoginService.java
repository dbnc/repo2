package com.bugs.service;

import com.bugs.entity.User;
import com.bugs.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private UserMapper mapper;

    public Map<String, Object> selectByEmail(String email, String password) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("email+++++"+email);
//        System.out.println(mapper.selectByEmail(email));
        User user = new User();
        if(mapper.selectByEmail(email)==null) {
            user = null;
        }else {
            user = mapper.selectByEmail(email);
        }
        if (user == null) {
            map.put("code", "账号不存在<a href='/'>点此重新登陆</a>");
            return map;
        } else if (!user.getPassword().equals(password)) {
            map.put("code", "密码错误<a href='/'>点此重新登陆</a>");
            return map;
        }
        map.put("permissions", user.getPermissions());
        map.put("code", null);
        return map;
    }

    public Map<String, Object> updatePassword(String email, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        mapper.updatePasswordByEmail(email, oldPassword, newPassword);
        if (mapper.selectPasswordByEmail(email).equals(newPassword)) {
            System.out.println("修改成功");
            map.put("code", null);
        } else {
            map.put("code", "原密码错误<a href='/user/Personal'>点此重新修改</a>");
        }
        return map;
    }

}
