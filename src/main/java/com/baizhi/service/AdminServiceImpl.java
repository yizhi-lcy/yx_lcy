package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminDao ad;
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin select(String name) {
        return ad.select(name);
    }

    @Override
    public void insert(Admin admin) {
        ad.insert(admin);
    }

    @Override
    public void delete(String id) {
        ad.delete(id);
    }

    @Override
    public List<Admin> selects() {
        return ad.selects();
    }

    @Override
    public void update(Admin admin) {
        ad.update(admin);
    }
}
