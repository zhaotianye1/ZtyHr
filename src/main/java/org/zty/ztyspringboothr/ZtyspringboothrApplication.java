package org.zty.ztyspringboothr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.zty.ztyspringboothr.mapper")
public class ZtyspringboothrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZtyspringboothrApplication.class, args);
    }

}
