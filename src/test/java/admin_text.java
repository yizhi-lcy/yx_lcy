import com.baizhi.YXApplication;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = YXApplication.class)
public class admin_text {
    @Autowired
    private AdminDao ad;
    @Test
    public void sel(){
        System.out.println(ad);
        System.out.println(ad.select("admin"));
    }
    @Test
    public void ins(){
        String id= UUID.randomUUID().toString();
        ad.insert(new Admin(id,"admin","123456",1));
    }
}
