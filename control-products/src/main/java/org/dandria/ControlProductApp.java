package org.dandria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ControlProductApp {
    public static void main(String[] args) {
        SpringApplication.run(ControlProductApp.class, args);
    }
} 