package com.baizhi.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.baizhi.config.AliyunConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class Aliyun {
    private static Logger log = LoggerFactory.getLogger(Aliyun.class);
   // private static OSS ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);

    public static void upload(MultipartFile photo, String fileName) {
        OSS ossClient = null;
        try {
            /*//获得原始文件名;
            String fileRealName = photo.getOriginalFilename();
            // 点号的位置
            int pointIndex = fileRealName.lastIndexOf(".");
            // 截取文件后缀
            String fileSuffix = fileRealName.substring(pointIndex);
            // 新文件名,UUID
            log.debug("Aliyun.upload 收参的time："+fileName);
            //新文件完整名（含后缀）
            String saveFileName =fileName.concat(fileSuffix);
            log.debug("Aliyun.upload字符串saveFileName："+saveFileName);*/
            /*String filePath = "D:\\FileAll\\" + saveFileName;
            log.debug("Aliyun.upload字符串filePath："+filePath);
            File path = new File(filePath);
            //判断文件路径下的文件夹是否存在，不存在则创建
            if (!path.exists()) {
                path.mkdirs();
            }
            File savedFile = new File(filePath);*/


            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);
            // 创建PutObjectRequest对象。 参数：Bucket名字，指定文件名，文件本地路径
            //PutObjectRequest putObjectRequest = new PutObjectRequest("yingx-leicy", objectName, new File(localFile));

            // 上传Byte数组。
            byte[] content = photo.getBytes();
            ossClient.putObject(AliyunConfig.BUCKETNAME, fileName, new ByteArrayInputStream(content));


            // 上传文件流。
            //InputStream inputStream = new FileInputStream(localFile);
            //上传文件
            //ossClient.putObject(AliyunConfig.BUCKETNAME, objectName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null)
                // 关闭OSSClient。
                ossClient.shutdown();
        }
    }
                                    //视频路径       图片路径
    public static void grabFrame(String videoName,String photoPath) {
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);
           // String[] split = photoName.split("\\.");
            String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
            //String signedUrl = videoName + "video/snapshot,t_50000,f_jpg,w_800,h_600";

            Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(AliyunConfig.BUCKETNAME, videoName, HttpMethod.GET);
            req.setExpiration(expiration);
            req.setProcess(style);
            URL signedUrl = ossClient.generatePresignedUrl(req);

            //填写网络流地址。
            InputStream inputStream = new URL(signedUrl.toString()).openStream();
            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。
            // Object完整路径中不能包含Bucket名称。
            //String photoPath = split[0] + ".jpg";
            ossClient.putObject(AliyunConfig.BUCKETNAME, photoPath, inputStream);
            System.out.println("永久存储地址" + photoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void capture(String fileName) {
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);
            // 设置视频截帧操作。
            String style = "video/snapshot,t_50000,f_jpg,w_800,h_600";
            // 指定过期时间为10分钟。
            Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(AliyunConfig.BUCKETNAME, fileName, HttpMethod.GET);
            req.setExpiration(expiration);
            req.setProcess(style);
            URL signedUrl = ossClient.generatePresignedUrl(req);
            System.out.println(signedUrl);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
//            sClient.shutdown();
        }
    }

    public static void delete(String objectName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        //String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        //String accessKeyId = "<yourAccessKeyId>";
        //String accessKeySecret = "<yourAccessKeySecret>";
       /* String bucketName = "yingx-test";  //存储空间名
        String objectName = "2020宣传视频.mp4";  //文件名*/

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(AliyunConfig.BUCKETNAME, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
//下载文件
    public static void downLoad(String objectName){
        /*int length = AliyunConfig.ALIYUN_PATH.length();
        // 截取文件路径
        String name =objectName.substring(length);*/
        String localFile="D:\\"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(AliyunConfig.BUCKETNAME, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
