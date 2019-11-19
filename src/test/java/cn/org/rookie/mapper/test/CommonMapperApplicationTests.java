package cn.org.rookie.mapper.test;

import cn.org.rookie.mapper.mapper.DemoMapper;
import cn.org.rookie.mapper.service.DemoService;
import cn.org.rookie.mapper.sql.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonMapperApplicationTests {

    @Autowired
    DemoMapper demoMapper;
    @Autowired
    DemoService demoService;

    @Test
    public void contextLoads() {
        demoMapper.selectOne(Wrapper.build().eq("id", "0"));
        demoMapper.selectByPrimary("dd");
    }

}
