package com.example.yingbang.controller;

import com.example.yingbang.dao.MovieDao;
import com.example.yingbang.result.ResultCodeEnum;
import com.example.yingbang.result.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetmovieController {
    @Autowired
    MovieDao movieDao;

    @PostMapping("/getmoiveindex")
    public ReturnResult getmoiveindex(@RequestParam("index") Integer index){
        ReturnResult r=ReturnResult.getState(ResultCodeEnum.GET_MOVIE_SUCCESS);
        if(movieDao.checkIndex() < index){
            r=ReturnResult.getState(ResultCodeEnum.GET_MOVIE_ERROR);
        }
        if(r.getSuccess()){
            int temp = index - 1;
            r.data("movie", movieDao.getMovieIndex(temp));
        }
        return r;
    }
}
