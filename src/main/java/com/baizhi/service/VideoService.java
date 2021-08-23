package com.baizhi.service;

import com.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    //增
    void insert(MultipartFile photo,Video video);
    //删
    void delete(String id,String videoPath,String photoPath);
    //分页查全部
    List<Video> selectByPage(@Param("start") int start, @Param("count") int count);
    //查全部页数
    int size();
}
