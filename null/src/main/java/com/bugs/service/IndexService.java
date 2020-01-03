package com.bugs.service;


import com.bugs.entity.Movies;
import com.bugs.mapper.IndexMovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    @Autowired
    private IndexMovieMapper indexMovieMapper;

    public List<Movies> sendListByPage(Integer startNum) {
        Integer page = (startNum - 1) * 5;
        List<Movies> moviesList = indexMovieMapper.sendListByPage(page);
        return moviesList;
    }

    public Integer sendAll() {
        return indexMovieMapper.sendAll().size();
    }
}
