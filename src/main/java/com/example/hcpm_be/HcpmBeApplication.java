package com.example.hcpm_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
//@EnableSwagger2
@EnableWebMvc
public class HcpmBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HcpmBeApplication.class, args);
    }

}

