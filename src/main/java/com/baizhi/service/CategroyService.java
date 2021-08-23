package com.baizhi.service;

import com.baizhi.entity.Categroy;

import java.util.List;

public interface CategroyService {
    //根据级别查全部类型
    List<Categroy> selectByLevels(int levels);
    //按照上级类别id查全部下级类别
    List<Categroy> selectByTwo(String id);
    //删除类别
    void delete(String id);
    //添加类别
    void insert(Categroy categroy);
    //修改类别
    void update(Categroy categroy);
    //根据名字查一个
    Categroy selectByName(String name);
}
