package com.vv.tool.photos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author vv
 * @date 2023/6/20 11:38
 */
@SpringBootApplication(scanBasePackages = "com.vv.tool.photos")
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
