package com.example.yingbang.controller;

import com.example.yingbang.dao.MovieDao;
import com.example.yingbang.result.ResultCodeEnum;
import com.example.yingbang.result.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

public class GetmovieController {
    @Autowired
    MovieDao movieDao;

    @PostMapping("/getmoiveindex")
    public ReturnResult getmoiveindex(int I){
        ReturnResult r=ReturnResult.getState(ResultCodeEnum.GET_MOVIE_SUCCESS);
        if(movieDao.checkIndex() < I){
            r=ReturnResult.getState(ResultCodeEnum.GET_MOVIE_ERROR);
        }
        if(r.getSuccess()){
            Map<String, Object> map = null;
            map.put("movie", movieDao.getMovieIndex(I));
            r.data(map);
        }
        return r;
    }
}
