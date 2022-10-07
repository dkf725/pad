package com.pad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/*开启定时任务*/
@EnableScheduling
@SpringBootApplication
        //(exclude = SecurityAutoConfiguration.class)
public class PadApplication {
    public static void main(String[] args) {
        SpringApplication.run(PadApplication.class);
    }
}
