package com.example.yingbang.dao;

import com.example.yingbang.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @version v1.0
 * @ClassName UserDao
 * @Description TODO
 * @Author bong
 * @Date 2021/10/25 23:44
 **/
@Repository
public interface UserDao {
    public int checkRegisterEmail(@Param("email") String email);
    public int checkRegisterUsername(@Param("username") String username);
    public int addUser(User user);
}
