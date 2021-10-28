package com.example.yingbang.dao;

import com.example.yingbang.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @version v1.0
 * @ClassName MovieDao
 * @Description TODO
 * @Author bong
 * @Date 2021/10/25 23:44
 **/
@Repository
public interface MovieDao {
    public int checkIndex();
    public int checkRegisterID(int id);
    public int addMovie(Movie movie);
    public Movie getMovieIndex(int index);
}
