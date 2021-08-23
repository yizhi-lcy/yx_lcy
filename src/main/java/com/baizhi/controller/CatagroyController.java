package com.baizhi.controller;

import com.baizhi.entity.Categroy;
import com.baizhi.service.CategroyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("category")
public class CatagroyController {
    private Logger log= LoggerFactory.getLogger(CatagroyController.class);
    @Autowired
    private CategroyService cs;

    //根据级别查询所有
    @RequestMapping("queryByLevels")
    public HashMap<String,Object> queryByLevels(int level){
        HashMap<String,Object> hm=new HashMap<>();
        try {
            List<Categroy> categroys = cs.selectByLevels(level);
            hm.put("categroys",categroys);
            hm.put("result","ok");
        }catch (Exception e){
            e.printStackTrace();
            hm.put("result","error");
        }finally {
            return hm;
        }
    }
    //根据上级类别查询所有夏季类别
    @RequestMapping("queryByParentId")
    public HashMap<String,Object> queryByParentId(String id){
        log.debug("根据上级类别查询所有夏季类别   上级id："+id);
        HashMap<String,Object> hm=new HashMap<>();
        try {
            List<Categroy> categroys = cs.selectByTwo(id);
            hm.put("categroys",categroys);
            hm.put("result","ok");
        }catch (Exception e){
            e.printStackTrace();
            hm.put("result","error");
        }finally {
            return hm;
        }
    }
    //添加标签
    @PostMapping("save")
    public HashMap<String,String> save(@RequestBody Categroy categroy){
        log.debug("添加标签："+categroy);
        HashMap<String,String> hm=new HashMap<>();
        try {
            Categroy categroy1 = cs.selectByName(categroy.getCate_name());
            log.debug("标签："+categroy1);
            if(categroy1!=null){
                hm.put("result","此类型已存在");
                hm.put("bool","no");
                log.debug("此类型已存在");
            }
            else {
                String id = UUID.randomUUID().toString();
                categroy.setId(id);
                cs.insert(categroy);
                hm.put("result", "添加成功");
                hm.put("bool", "ok");
                log.debug("成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            hm.put("result","error");
        }finally {
            return hm;
        }
    }
    //删除标签
    @RequestMapping("delete")
    public HashMap<String,String> delete(String id){
        log.debug("删除标签："+id);
        HashMap<String,String> hm=new HashMap<>();
        try {
            List<Categroy> list = cs.selectByTwo(id);
            log.debug("分类内还有子分类数量："+list.size());
            if(list.size()!=0){
                log.debug("分类内还有子分类数量："+list);
                hm.put("result","此类别还有下级分类，不可直接删除！");
                hm.put("bool","no");
            }
            else {
                cs.delete(id);
                hm.put("bool", "ok");
                log.debug("成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            hm.put("result","error");
        }finally {
            return hm;
        }
    }
    //修改标签
    @PostMapping("chage")
    public String chage(@RequestBody Categroy categroy){
        try {
            cs.update(categroy);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
