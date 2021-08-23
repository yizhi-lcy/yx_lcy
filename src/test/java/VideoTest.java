import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.baizhi.YXApplication;
import com.baizhi.config.AliyunConfig;
import com.baizhi.util.Aliyun;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@SpringBootTest(classes = YXApplication.class)
public class VideoTest {
    @Test
    public void uploadAliyunInputStream() throws Exception {
      /*  String objectName = "AA.png";  //文件名  可以指定文件目录
        String localFile="D:\\乱七八糟\\AA.png";  //本地视频路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);

        // 上传文件流。
        InputStream inputStream = new FileInputStream(localFile);
        //上传文件
        ossClient.putObject(AliyunConfig.BUCKETNAME, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();*/

        //视频截取
        // 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        String objectName = "video/4518143b483394c38d5d421dcbb3bcb9.mp4";
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(AliyunConfig.ENDPOINT, AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);
// 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
// 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(AliyunConfig.BUCKETNAME, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
// 关闭OSSClient。
        ossClient.shutdown();

    }
    @Test
    public void aa(){
        String aa="https://yingx-leicy.oss-cn-beijing.aliyuncs.com/video/05f77dcad226e6b3cd7f6d26eb8495b6.mp4";
        String bb="https://yingx-leicy.oss-cn-beijing.aliyuncs.com/";
    }
}
