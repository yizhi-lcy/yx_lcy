import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.YXApplication;
import com.baizhi.config.AliyunConfig;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.util.Aliyun;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = YXApplication.class)
public class user_text {
    @Autowired
    private UserDao ud;

    @Test
    public void yingx(){
        GoEasy goEasy = new GoEasy( "http(s)://hangzhou.goeasy.io", "BC-7c2bfaf2510c4a6f9a16fde1448bde01");
                goEasy.publish("yingx", "Hello GoEasy");

           /*     ,new PublishListener(){
            @Override
            public void onSuccess() {
                System.out.println("Publish success.");
            }

            @Override
            public void onFailed(GoEasyError error) {
                System.out.println("Failed to Publish message, error:" + error.getCode() + " , " + error.getContent());
            }
        }*/

    }

    @Test
    public void testExport() throws Exception{
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

        workbook.write(new FileOutputStream(new File("D://easypoi.xls")));
        workbook.close();
        //最后删除文件夹head
        for (User user : all) {
            File head=new File(user.getHead());
            head.delete();
        }
        deleteDir(dir);//利用递归删除文件
    }
    static void deleteDir(File dir){
        File[] fs=dir.listFiles();
        for(File f:fs){//循环删除文件，循环结束文件夹为空
            if(f.isFile())f.delete();//判断是不是文件，是则删除
            if(f.isDirectory())deleteDir(f);//判断是不是文件夹，是则递归，调用本方法
        }
        dir.delete();//删除空文件夹
    }

    /*@Test
    public void delete(){

    }*/

    @Test
    public void add(){
        String id1= UUID.randomUUID().toString();
        String id2= UUID.randomUUID().toString();
        User u1 = new User(id1, "22222222222", "bbb", "", "应学测试22", "bbbbbbb", null, 1);
        ud.insert(u1);
        User u2 = new User(id2, "11111111111", "aaa", "", "应学测试11", "aaaaaaa", null, 1);
        ud.insert(u2);
    }
    @Test
    public void sel(){
        /*System.out.println(ud.select("11111111111"));
        System.out.println("-------------------------------");
        System.out.println(ud.selectByPage(0, 2));*/
        System.out.println(ud.size());
    }
}
