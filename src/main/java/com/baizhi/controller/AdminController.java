package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {
    private Logger log= LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService as;

    //登录  @RequestBody
    @PostMapping("login")
    public HashMap<String, Object> login(@RequestBody Admin admin) {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        log.debug("登录的账户："+admin);
        try {
            Admin admin1 = as.select(admin.getUsername());
            if (admin1 == null) {
                //用户不存在
                hm.put("result", "用户名不存在");
            } else if(admin1.getStatus()==1){
                if (admin.getPassword().equals(admin1.getPassword())) {
                    //登录成功
                    hm.put("result", "ok");
                    hm.put("admin", admin1.getUsername());
                } else {
                    //用户名或密码不正确
                    hm.put("result", "用户名或密码不正确");
                }
            }else {
                hm.put("result", "当前账户已冻结");
            }
        } catch (Exception e) {
            e.printStackTrace();
            hm.put("result", "error");
        } finally {
            return hm;
        }
    }

    //注册
    @PostMapping("register")
    public String register(@RequestBody Admin admin) {
        try {
            Admin admin1 = as.select(admin.getUsername());
            if (admin1 != null) {
                return "用户已存在";
            }
            String id = UUID.randomUUID().toString();
            admin.setId(id);
            as.insert(admin);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    //修改用户信息
    @PostMapping("change")
    public String change(@RequestBody Admin admin){
        try {
            as.update(admin);
            return "修改成功！";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    //查询所有用户信息
    @PostMapping("query")
    public HashMap<String, Object> query(){
        HashMap<String, Object> hm = new HashMap<String, Object>();
        try {
            List<Admin> list = as.selects();
            hm.put("result",list);
        }catch (Exception e){
            e.printStackTrace();
            hm.put("result","error");
        }finally {
            return hm;
        }
    }
}
