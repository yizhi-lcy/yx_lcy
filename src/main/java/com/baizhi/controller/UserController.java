package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.Upload;
import com.baizhi.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService us;

    @RequestMapping("FX")
    public HashMap<String, Object> FX(){
        HashMap<String, Object> date = us.selectDate();
        /*Set<String> set = date.keySet();
        for (String s : set) {
            System.out.println(s+"  "+date.get(s));
        }*/

        return date;
    }

    @RequestMapping("del")
    public HashMap<String, Object> del(String id,String photoPath){
        HashMap<String, Object> date=new HashMap<>();
        try {
            log.debug("传过来的id："+id);
            log.debug("传过来的photoPath："+photoPath);
            us.delete(id,photoPath);
            date = us.selectDate();
        /*Set<String> set = date.keySet();
        for (String s : set) {
            System.out.println(s+"  "+date.get(s));
        }*/

            date.put("result","ok");
        }catch (Exception e){
            e.printStackTrace();
            date.put("result","error");
        }
        return date;
    }

    @RequestMapping("export")
    public String export(){
        log.debug("导出用户信息");
        try {
            us.export();
            log.debug("导出成功");
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("cha")
    //public String cha(String id,String phone, String username, String brief, MultipartFile photo){
    public String cha(String id,String username, String brief){
        try {
            User user = new User(id, null, username, null, brief, null, null, 1);
            us.update(user);
//            String id = UUID.randomUUID().toString();
            //上传头像
            /*log.debug(photo.getOriginalFilename());
            String time = new Date().getTime()+"";
            Boolean up = Upload.up(photo, time);
            String fileRealName = photo.getOriginalFilename();
            //获得原始文
            int pointIndex = fileRealName.lastIndexOf(".");
            // 点号的位置
            String fileSuffix = fileRealName.substring(pointIndex);
            // 截取文件后缀
            us.insert(new User(id, phone, username, "https://1234-1304296293.cos.ap-nanjing.myqcloud.com/headimg/" + time + fileSuffix, brief, null, null, 1));
            hm.put("result", "ok");*/
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("add")//添加
    public HashMap<String, Object> add(String phone, String username, String brief, MultipartFile photo) {
        HashMap<String, Object> hm = new HashMap<>();
        try {
            User u = us.select(phone);
            if (u != null) {
                hm.put("result", "no");
                hm.put("str", "此手机号已存在，请登录！");
            } else {
                /*String id = UUID.randomUUID().toString();
                //上传头像
                log.debug(photo.getOriginalFilename());
                String time = new Date().getTime()+"";
                Boolean up = Upload.up(photo, time);
                String fileRealName = photo.getOriginalFilename();
                //获得原始文件名;
                int pointIndex = fileRealName.lastIndexOf(".");
                // 点号的位置
                String fileSuffix = fileRealName.substring(pointIndex);
                // 截取文件后缀
                us.insert(new User(id, phone, username, "https://1234-1304296293.cos.ap-nanjing.myqcloud.com/headimg/" + time + fileSuffix, brief, null, null, 1));*/
                User user=new User();
                user.setPhone(phone);
                user.setUsername(username);
                user.setBrief(brief);

                us.insert(photo,user);
                hm = us.selectDate();
                hm.put("result", "ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
            hm.put("result", "error");
        }finally {
            return hm;
        }
    }

    @GetMapping("allpage")
    public HashMap<String, Object> queryByPage(int page, int count) {
        page=1;
        count=10;
        log.debug("分页查，传递过来的页数和最大条数" + page + "   " + count);
        HashMap<String, Object> hm = new HashMap<>();
        try {
            int size = us.size();

            /*int len;
            if(size%2==0)len=size/2;
            else len=size/2+1;
            hm.put("size",len);*/

            hm.put("size", size);
            hm.put("page", page);
            List<User> users = us.selectByPage(2 * (page - 1), count);
            hm.put("result", "ok");
            hm.put("users", users);
        } catch (Exception e) {
            e.printStackTrace();
            hm.put("result", "error");
        } finally {
            return hm;
        }
    }

    /*@RequestMapping("status")
    public String status(@RequestBody User user){*/
    @GetMapping("status")
    public String status(String id, Integer status) {
        log.debug("修改状态信息" + id + "   " + status);
        try {
            //int status=user.getStatus()==0?1:0;

            User user = new User();
            user.setId(id);
            user.setStatus(status);
            us.updateStatus(user);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
