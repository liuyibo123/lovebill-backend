package com.liuyibo.lovebill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LovebillApplication {

    public static void main(String[] args) {
        SpringApplication.run(LovebillApplication.class, args);
    }
}
