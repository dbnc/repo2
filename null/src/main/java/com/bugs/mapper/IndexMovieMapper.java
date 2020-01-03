package com.bugs.mapper;

import com.bugs.entity.Movies;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexMovieMapper {
    /**
     * 分页显示
     * @return
     */
    public List<Movies> sendListByPage(Integer startNum);
    public List<Movies> sendAll();
}
