import com.tang.patent.dao.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTest {
    @Autowired
    UserMapper userMapper;



    @Test
    public void ServiceConfig(){
        userMapper.countUser();
//        userMapper.countUserByMonth(2);
    }
}
