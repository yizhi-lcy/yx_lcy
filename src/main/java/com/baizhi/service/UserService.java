package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    //分页查
    List<User> selectByPage(int start, int count);
    //添加
    void insert(MultipartFile photo, User user);
    //删
    void delete(String id,String photoPath);
    //改
    void update(User user);
    //改
    void updateStatus(User user);
    //查一个
    User select(String phone);
    //查询总条数
    int size();
    //查询用户信息,并导出
    void export();
    //根据月份查询用户每月注册人数
    HashMap<String,Object> selectDate();
}
