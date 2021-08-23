package com.baizhi.service;

import com.baizhi.config.AliyunConfig;
import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Video;
import com.baizhi.util.Aliyun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class VideoServiceImpl implements VideoService{
    @Autowired
    private VideoDao vd;
    @Override
    public void insert(MultipartFile photo,Video video) {
        video.setId(UUID.randomUUID().toString());
        //获得原始文件名;
        String fileRealName = photo.getOriginalFilename();
        // 点号的位置
        int pointIndex = fileRealName.lastIndexOf(".");
        // 截取文件后缀
        String fileSuffix = fileRealName.substring(pointIndex);
        //新的视频文件名，不加后缀的
        long time = new Date().getTime();
        String fileName1="video/"+time;
        //缩略图文件名
        String fileName2="video_head/"+time+".jpg";
        // 新文件名,UUID
        //log.debug("Aliyun.upload 收参的time："+fileName);
        //新文件完整名（含后缀）
        String saveFileName =fileName1.concat(fileSuffix);
        //String saveFileName2 =fileName2.concat(fileSuffix);
        //String path=saveFileName+"?x-oss-process=video/snapshot,t_1000,f_jpg,w_800,h_600,m_fast";
        //log.debug("Aliyun.upload字符串saveFileName："+saveFileName);
        //上传云端视频
        Aliyun.upload(photo,saveFileName);
        Aliyun.grabFrame(saveFileName,fileName2);
        video.setVideoPath(AliyunConfig.ALIYUN_PATH +saveFileName);
        video.setCoverPath(AliyunConfig.ALIYUN_PATH +fileName2);
        video.setMins(new Date());
        vd.insert(video);
    }

    @Override
    public void delete(String id,String videoPath,String photoPath) {
        int length = AliyunConfig.ALIYUN_PATH.length();
        // 截取文件路径
        String fileSuffix1 = videoPath.substring(length);
        String fileSuffix2 = photoPath.substring(length);
        //删除云端视频
        Aliyun.delete(fileSuffix1);
        Aliyun.delete(fileSuffix2);
        vd.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Video> selectByPage(int start, int count) {
        return vd.selectByPage(start,count);
    }

    //查全部页数
    public int size(){
        return vd.size();
    }
}
