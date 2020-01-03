package com.bugs.mapper;

import com.bugs.entity.User;
import com.bugs.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    /**
     * 添加正在播放的影片到到播放记录
     * @param email
     * @param url
     */
    public void insertUrlToHistory(@Param("email") String email,@Param("url") String url);

    /**
     * 删除播放记录中的某个影片
     * @param email
     * @param url
     */
    public void deleteUrlToHistory(@Param("email") String email,@Param("url") String url);

    /**
     * 查找播放记录中的某一部影片
     * @param email
     * @param url
     * @return
     */
    public UserInfo sendHistory(@Param("email") String email, @Param("url") String url);

    /**
     * 安页查找影片
     * @return
     */
    public List<UserInfo> sendHistoryList(@Param("email")String email,@Param("startNum") Integer startNum);
    /**
     * 添加到收藏中
     * @param email
     * @param url
     */
    public void insertUrlToCollection(@Param("email") String email,@Param("url") String url);

    /**
     * 从收藏中删除该记录
     * @param email
     * @param url
     */
    public void deleteUrlToCollection(@Param("email") String email,@Param("url") String url);

    /**
     * 查找收藏中的某一记录
     * @param email
     * @return
     */
    public UserInfo sendCollection(@Param("email") String email,@Param("url") String url);

    /**
     * 收藏中的记录  按页查询
     * @param email
     * @return
     */
    public List<UserInfo> sendCollectionList(@Param("email") String email,@Param("startNum") Integer startNumber);
}
