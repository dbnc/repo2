package com.bugs.mapper;

import com.bugs.entity.Movies;
import com.bugs.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> sendAll();

    public User send(@Param("email") String email, @Param("password") String password);

    public User selectByEmail(String email);

    public User selectByPassword(String password);

    //无法使用  因使用别名
//    public void insertUser(@Param("user") User user);
    public void insertUser(@Param("userId") String userId,
                           @Param("email") String email,
                           @Param("username") String username,
                           @Param("password") String password,
                           @Param("permissions") int permissions);

    /**
     * 根据email修改密码
     */
    public void updatePasswordByEmail(@Param("email") String email,
                                      @Param("oldPassword") String oldPassword,
                                      @Param("newPassword") String newPassword);

    /**
     * 查询密码
     */
    public String selectPasswordByEmail(@Param("email") String email);

    /**
     * 根据页数查询
     */
    public List<User> sendByPage(@Param("startNum") Integer startNum);

    /**
     * 根据页数查询admin
     */
    public List<User> sendAdminByPage(@Param("startNum") Integer startNum);

    /**
     * 修改用户信息
     */
    public void updateUser(@Param("oldEmail") String oldEmail,
                           @Param("email") String email,
                           @Param("username") String username,
                           @Param("password") String password,
                           @Param("permissions") Integer permissions);

    /**
     * 删除用户
     */
    public void deleteUser(@Param("email")String email);

    public void updateAdmin(@Param("email") String email, @Param("username") String username,@Param("password") String password,@Param("permissions") int permissions);


    public void insertAdmin(@Param("userId") String userId,
                            @Param("email") String email,
                            @Param("username") String username,
                            @Param("password") String password,
                            @Param("permissions") int permissions);
}
