package com.baizhi.service;

import com.baizhi.dao.CategroyDao;
import com.baizhi.entity.Categroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CategroyServiceImpl implements CategroyService{
    @Autowired
    private CategroyDao cd;
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Categroy> selectByLevels(int levels) {
        return cd.selectByLevels(levels);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Categroy> selectByTwo(String id) {
        return cd.selectByTwo(id);
    }

    @Override
    public void delete(String id) {
        cd.delete(id);
    }

    @Override
    public void insert(Categroy categroy) {
        cd.insert(categroy);
    }

    @Override
    public void update(Categroy categroy) {
        cd.update(categroy);
    }
    //根据名字查一个
    public Categroy selectByName(String name){
        return cd.selectByName(name);
    }
}
