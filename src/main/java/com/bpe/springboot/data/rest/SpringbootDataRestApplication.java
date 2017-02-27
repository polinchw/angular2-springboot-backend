package com.bpe.springboot.data.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootDataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDataRestApplication.class, args);
    }
}
