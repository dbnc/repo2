package com.bugs.service;

import com.bugs.entity.Movies;
import com.bugs.mapper.MoviesMapper;
import com.bugs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieListService {
    @Autowired
    private MoviesMapper moviesMapper;

    public Integer sendAll() {
        return  moviesMapper.sendAll().size()/50;
    }

    /**
     * @param page 当前所在页
     * @return
     */
    public List<Movies> sendListByPage(Integer page) {
        Integer pages = (page - 1) * 24;
        List<Movies> movies = moviesMapper.sendListByPage(pages);
        return movies;

    }

    public void deleteResource(String movieUrl){
        moviesMapper.deleteMovie(movieUrl);
        System.out.println("movieUrl="+movieUrl+"   的电影已删除成功");
    }

    public Map<String,Object> insertMovie(Movies movie){
        Map<String, Object> map = new HashMap<>();
        List<Movies> movies = moviesMapper.sendByUrl(movie.getMovieUrl());
        if(movies==null) {
            moviesMapper.insertMovie(movie.getMovieId(),
                    movie.getMovieImg(),
                    movie.getMovieName(),
                    movie.getMovieUrl(),
                    movie.getMovieInfo());
            map.put("code", null);
        }else {
            map.put("code", "内容已存在，不必再添加");
        }
        return map;
    }

    public List<Movies> sendByName(String seachName){
        return moviesMapper.sendByName(seachName);
    }
}
