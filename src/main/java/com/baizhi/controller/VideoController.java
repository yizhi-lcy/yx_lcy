package com.baizhi.controller;

import com.baizhi.entity.Categroy;
import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("video")
@CrossOrigin
public class VideoController {
    private Logger log= LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService vs;

    @RequestMapping("add")
    public String add(MultipartFile video,String title,String cateId,String brief){
        log.debug("添加视频");
        log.debug("视频"+video);
        //log.debug(vs.toString());
        try {
            Video v = new Video();
            Categroy categroy = new Categroy();
            categroy.setId(cateId);
            /*User user = new User();
            user.setId(brief);*/
            v.setTitle(title);
            v.setCategroy(categroy);
            v.setBrief(brief);

            vs.insert(video,v);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    @RequestMapping("del")
    public String del(String id,String videoPath,String photoPath){
        try {
            vs.delete(id,videoPath,photoPath);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    @RequestMapping("querys")
    public HashMap<String,Object> queryByPage(int page,int count){
        log.debug("分页查，传递过来的页数和最大条数" + page + "   " + count);
        HashMap<String, Object> hm = new HashMap<>();
        try {
            int size = vs.size();
            hm.put("size", size);
            log.debug("最大条数："+size);

            /*int len;
            if(size%2==0)len=size/2;
            else len=size/2+1;
            hm.put("size",len);*/

            hm.put("page", page);
            List<Video> videos = vs.selectByPage(2 * (page - 1), count);
            for (Video video : videos) {
                System.out.println(video);
            }
            hm.put("result", "ok");
            hm.put("videos", videos);
        } catch (Exception e) {
            e.printStackTrace();
            hm.put("result", "error");
        } finally {
            return hm;
        }
    }
}
