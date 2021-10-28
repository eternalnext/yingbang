package com.example.yingbang.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName ReturnResult
 * @Description TODO
 * @Author bong
 * @Date 2021/10/26 10:37
 **/
@Data
public class ReturnResult{
    private Boolean success;
    private Integer code;
    private String message;
    private String data = "";
    public static ReturnResult getState(ResultCodeEnum re){
        ReturnResult r=new ReturnResult();
        r.setSuccess(re.getSuccess());
        r.setCode(re.getCode());
        r.setMessage(re.getMessage());
        return r;
    }

    public ReturnResult data(String s){
        this.setData(s);
        return this;
    }
}
