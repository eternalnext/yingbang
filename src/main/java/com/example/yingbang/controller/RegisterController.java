package com.example.yingbang.controller;

import com.example.yingbang.bean.User;
import com.example.yingbang.dao.UserDao;
import com.example.yingbang.result.ResultCodeEnum;
import com.example.yingbang.result.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version v1.0
 * @ClassName RegisterController
 * @Description TODO
 * @Author bong
 * @Date 2021/10/26 0:14
 **/
@RestController
public class RegisterController {
    @Autowired
    UserDao userDao;

    @PostMapping("/register")
    public ReturnResult register(@RequestBody User user){
        ReturnResult r=ReturnResult.getState(ResultCodeEnum.REGISTER_SUCCESS);
        if(userDao.checkRegisterUsername(user.getUsername())>0){
            r=ReturnResult.getState(ResultCodeEnum.REGISTER_USERNAME_ERROR);
        }
        if(userDao.checkRegisterEmail(user.getEmail())>0){
            r=ReturnResult.getState(ResultCodeEnum.REGISTER_EMAIL_ERROR);
        }
        if(r.getSuccess()){
            userDao.addUser(user);
        }
        return r;
    }

}
