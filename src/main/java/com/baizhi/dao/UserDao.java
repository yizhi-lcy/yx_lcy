package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //分页查
    List<User> selectByPage(@Param("start")int start,@Param("count") int count);
    //添加
    void insert(User user);
    //删
    void delete(String id);
    //改
    void update(User user);
    //改
    void updateStatus(User user);
    //查一个
    User select(String phone);
    //查询总条数
    int size();
    //查询用户信息
    List<User> selectAll();
    //根据月份查询用户每月注册人数
    List<UserVO> selectDate(String sex);
}
