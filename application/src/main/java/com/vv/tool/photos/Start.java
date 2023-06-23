package com.vv.tool.photos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 11:38
 */
@SpringBootApplication(scanBasePackages = "com.vv.tool.photos")
@EnableElasticsearchRepositories(basePackages = "com.vv.tool.photos.es.**")
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
