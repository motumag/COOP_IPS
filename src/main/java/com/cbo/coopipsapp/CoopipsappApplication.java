package com.cbo.coopipsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AutoConfiguration
@EnableScheduling
@EnableCaching
public class CoopipsappApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoopipsappApplication.class, args);
    }

}
