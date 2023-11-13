package com.jasmine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootWebApplication.class, args);
    }

}
