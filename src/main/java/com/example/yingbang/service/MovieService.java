package com.example.yingbang.service;

import com.example.yingbang.dao.MovieDao;
import com.example.yingbang.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    MovieDao moviedao;

    public void save(Movie movie) {
        moviedao.addMovie(movie);
    }
    public boolean cheackID(int id) {
        if(moviedao.checkRegisterID(id) > 0){
            return true;
        }else{
            return false;
        }
    }
}
