package com.bugs.controller;

import com.bugs.entity.Movies;
import com.bugs.entity.User;
import com.bugs.mapper.IndexMovieMapper;
import com.bugs.mapper.MoviesMapper;
import com.bugs.mapper.UserMapper;
import com.bugs.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MyControllerTest {
    @Autowired
    private UserMapper mapper ;
    @RequestMapping("/test")
    @ResponseBody
    public List<User> sendAll(){
        List<User> users = mapper.sendAll();
        return users;
    }

//    @Autowired
//    private UserM

    @Autowired
    private IndexMovieMapper indexMovieMapper;
    @Autowired
    private IndexService indexService;

    @RequestMapping("/testMovie")
    @ResponseBody
    public List<Movies> sendListByPage(){
        //0开始计数
        List<Movies> movies = indexService.sendListByPage(3);
        return movies;
    }

    @Autowired
    private UserMapper userMapper;
//    @RequestMapping("/insert")
//    @ResponseBody
    public void insertUser(){
        userMapper.insertUser("7","9@qq.com","1","1",1);
        System.out.println("添加成功");
//        List<User> users = userMapper.sendAll();
//        System.out.println(users);
    }
}
