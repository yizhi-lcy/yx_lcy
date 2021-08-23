package com.baizhi.dao;

import com.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    //增
    void insert(Video video);
    //删
    void delete(String id);
    //分页查全部
    List<Video> selectByPage(@Param("start") int start,@Param("count") int count);
    //查全部页数
    int size();
}
