package com.bugs.service;

import com.bugs.entity.Movies;
import com.bugs.entity.User;
import com.bugs.entity.UserInfo;
import com.bugs.mapper.MoviesMapper;
import com.bugs.mapper.UserInfoMapper;
import com.bugs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 添加到历史列表
     *
     * @param email
     * @param url
     */
    public void insertUrlToHistory(String email, String url) {
        Map<String, String> map = sendHistory(email, url);
        if (map.get("code") == null) {
            userInfoMapper.insertUrlToHistory(email, url);
            System.out.println("已加入到播放历史");
        } else {
            System.out.println(map.get("code"));
        }
    }

    /**
     * 从历史列表删除
     *
     * @param email
     * @param url
     */
    public void deleteUrlToHistory(String email, String url) {
        userInfoMapper.deleteUrlToHistory(email, url);
        System.out.println("已从历史记录中删除");
    }

    /**
     * 保存到历史列表
     *
     * @param email
     * @return
     */
    private Map<String, String> sendHistory(String email, String url) {
        HashMap<String, String> map = new HashMap<>();
        UserInfo userInfo = userInfoMapper.sendHistory(email, url);
        if (userInfo != null) {
            map.put("code", "历史记录中已存在该记录");
        } else {
            map.put("code", null);
            System.out.println("历史记录中尚无该记录");
        }
        System.out.println("这是sendHistory  email==" + email);
        return map;
    }

    /**
     * 查找历史记录
     */
    public List<Movies> sendHistory(String email, Integer page) {
        List<Movies> movies = new ArrayList<>();
        int pg = (page - 1) * 24;
        List<UserInfo> userInfos = userInfoMapper.sendHistoryList(email, pg);
        System.out.println("        在查找历史记录中的email" + email);
        for (UserInfo userInfo : userInfos) {
            String movieUrl = userInfo.getMovieUrl();
            List<Movies> movies1 = moviesMapper.sendByUrl(movieUrl);
            for (Movies movies2 : movies1) {
                movies.add(movies2);
            }
        }
        return movies;
    }


    /**
     * 添加到收藏列表
     *
     * @param email
     * @param url
     */
    public void insertUrlToCollection(String email, String url) {
        Map<String, String> map = sendCollection(email, url);
        String code = map.get("code");
        if (code == null) {
            userInfoMapper.insertUrlToCollection(email, url);
            System.out.println("已加入到收藏列表");
        } else {
            System.out.println(code);
        }
    }

    /**
     * 从收藏列表中删除
     *
     * @param email
     * @param url
     */
    public void deleteUrlToCollection(String email, String url) {
        userInfoMapper.deleteUrlToCollection(email, url);
        System.out.println("已从收藏夹移除");
    }

    /**
     * 查询收藏列表某一记录
     */
    private Map<String, String> sendCollection(String email, String url) {
        UserInfo userInfo = userInfoMapper.sendCollection(email, url);
        Map<String, String> map = new HashMap<>();
        if (userInfo != null) {
            map.put("code", "收藏夹中已存在");
        } else {
            map.put("code", null);
        }
        return map;
    }

    @Autowired
    private MoviesMapper moviesMapper;

    /**
     * 查询收藏列表中的所有记录
     *
     * @param email
     * @return
     */
    public List<Movies> sendCollection(String email, int page) {
        int startNum = (page - 1) * 24;
        System.out.println("----email-------------" + email);
        List<UserInfo> userInfos = userInfoMapper.sendCollectionList(email, startNum);
        List<Movies> movies = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            String movieUrl = userInfo.getMovieUrl();
//            System.out.println("-------------------userinfo的movieUrl==" + movieUrl);
            List<Movies> movies1 = moviesMapper.sendByUrl(movieUrl);
//            System.out.println("movies1----------" + movies1);
            for (Movies movies2 : movies1) {
                movies.add(movies2);
            }
        }
        System.out.println("UserInfoService     sendCollection----------------");
//        System.out.println("---userinfos---" + userInfos);
        return movies;
    }

}
