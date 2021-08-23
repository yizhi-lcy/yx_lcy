package com.baizhi.dao;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminDao {
    //根据名字查一个
    Admin select(String name);
    //添加一个用户
    void insert(Admin admin);
    //删除一个用户
    void delete(String id);
    //查询全部用户
    List<Admin> selects();
    //修改用户信息
    void update(Admin admin);
}
