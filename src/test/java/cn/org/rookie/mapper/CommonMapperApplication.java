package cn.org.rookie.mapper;

import cn.org.rookie.interceptor.JoinTableInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("cn.org.rookie.mapper.mapper")
public class CommonMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMapperApplication.class, args);
    }

    @Bean
    public JoinTableInterceptor myHandler() {
        return new JoinTableInterceptor();
    }
}
