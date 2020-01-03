package com.bugs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MySpringboot {
    public static void main(String[] args) {
        SpringApplication.run(MySpringboot.class);
    }
}

