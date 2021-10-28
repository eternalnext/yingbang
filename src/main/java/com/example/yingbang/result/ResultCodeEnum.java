package com.example.yingbang.result;

import lombok.Getter;

/**
 * @version v1.0
 * @ClassName ResultCodeEnum
 * @Description TODO
 * @Author bong
 * @Date 2021/10/26 10:52
 **/
@Getter
public enum ResultCodeEnum {
    REGISTER_SUCCESS(true,20000,"注册成功"),
    REGISTER_EMAIL_ERROR(false,21000,"邮箱已存在"),
    REGISTER_USERNAME_ERROR(false,21001,"用户名称已存在"),
    GET_MOVIE_SUCCESS(true, 22000, "获取电影信息成功"),
    GET_MOVIE_ERROR(false, 22001, "获取电影信息失败"),
    ;

    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
