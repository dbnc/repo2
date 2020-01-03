package com.bugs.mapper;

import com.bugs.entity.Movies;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MoviesMapper {
    /**
     * 分页
     * @return
     */
    public List<Movies> sendListByPage(Integer pages);
    /**
     *用于获取size
     */
    public List<Movies> sendAll();

    /**
     * 根据url查询
     */
    public List<Movies> sendByUrl(@Param("url") String url);

    public void deleteMovie(@Param("movieUrl") String movieUrl);

    /**
     *
     */
    public void insertMovie(@Param("movieId") String id,
                            @Param("movieImg")String img,
                            @Param("movieName") String name,
                            @Param("movieUrl") String url,
                            @Param("movieInfo") String info);

    public List<Movies> sendByName(@Param("seachName") String seachName);
}
