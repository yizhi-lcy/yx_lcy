package com.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.config.AliyunConfig;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.util.Aliyun;
import com.baizhi.util.ExportUtil;
import com.baizhi.vo.UserVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao ud;
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectByPage(int start, int count) {
        return ud.selectByPage(start,count);
    }

    @Override
    public void insert(MultipartFile photo, User user) {
        user.setId(UUID.randomUUID().toString());

        //获得原始文件名;
        String fileRealName = photo.getOriginalFilename();
        // 点号的位置
        int pointIndex = fileRealName.lastIndexOf(".");
        // 截取文件后缀
        String fileSuffix = fileRealName.substring(pointIndex);
        //新的视频文件名，不加后缀的
        long time = new Date().getTime();
        String fileName="head/"+time;
        //新文件完整名（含后缀）
        String saveFileName =fileName.concat(fileSuffix);
        //上传云端图片
        Aliyun.upload(photo,saveFileName);
        user.setHead(AliyunConfig.ALIYUN_PATH +saveFileName);
        user.setStatus(1);
        user.setMins(new Date());

        ud.insert(user);
    }

    @Override
    public void delete(String id,String photoPath) {
        int length = AliyunConfig.ALIYUN_PATH.length();

        // 截取文件路径
        String fileSuffix = photoPath.substring(length);
        //删除云端视频
        Aliyun.delete(fileSuffix);

        ud.delete(id);
    }

    @Override
    public void update(User user) {
        ud.update(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public User select(String phone) {
        return ud.select(phone);
    }
    //改
    public void updateStatus(User user){
        ud.updateStatus(user);
    }
    //查询总条数
    @Transactional(propagation = Propagation.SUPPORTS)
    public int size(){
        return ud.size();
    }

    //导出
    @Transactional(propagation = Propagation.SUPPORTS)
    public void export(){
        List<User> users = ud.selectAll();
//        ExportUtil.export(users);

//注解方法导出文件
        File dir=new File("D:/head");
        dir.mkdir();
        List<User> all = ud.selectAll();

        int length = AliyunConfig.ALIYUN_PATH.length();
        for (User user : all) {
            // 截取文件路径
            String name =user.getHead().substring(length);
            Aliyun.downLoad(name);
            String localFile="D:\\"+name;  //下载本地地址  地址加保存名字
            user.setHead(localFile);
        }

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","user"),User.class, all);

        try {
            workbook.write(new FileOutputStream(new File("D://easypoi.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //最后删除文件夹head
        for (User user : all) {
            File head=new File(user.getHead());
            head.delete();
        }
        ExportUtil.deleteDir(dir);//利用递归删除文件

    }


    //根据月份查询用户每月注册人数
    @Transactional(propagation = Propagation.SUPPORTS) //该方法独立运行，不参与事务，如果其他业务方法调用会融入当前事务
    public HashMap<String,Object> selectDate(){
        HashMap<String, Object> hm=new HashMap<>();
        List<UserVO> musers = ud.selectDate("男");
        List<UserVO> wusers = ud.selectDate("女");
        ArrayList<String> x=new ArrayList<>();
        //x.toArray(new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"});
        ArrayList<Integer> ym=new ArrayList<>();
        ArrayList<Integer> yw=new ArrayList<>();

        /*for (UserVO user : musers) {
            if()
            ym.add(user.getCount());
        }*/
        for(int i=1;i<=12;i++){
            x.add(i+"月");
            ym.add(0);
            yw.add(0);
        }
        for (UserVO muser : musers) {
            //System.out.println(muser);
            ym.add(muser.getMins()-1,muser.getCount());
        }
        for (UserVO wuser : wusers) {
            //System.out.println(wuser);
            yw.add(wuser.getMins()-1,wuser.getCount());
        }
        hm.put("x",x);
        hm.put("ya",ym);
        hm.put("yb",yw);
        return hm;
    }
}
