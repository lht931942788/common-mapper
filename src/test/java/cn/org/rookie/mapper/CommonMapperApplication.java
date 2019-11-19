package cn.org.rookie.mapper;

import cn.org.rookie.mapper.annotation.EnableSpringUtils;
import cn.org.rookie.mapper.entity.Demo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.org.rookie.mapper.mapper")
@EnableSpringUtils
public class CommonMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMapperApplication.class, args);
    }

}
