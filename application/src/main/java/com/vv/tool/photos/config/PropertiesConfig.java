package com.vv.tool.photos.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 20:10
 */
@Data
@Configuration
public class PropertiesConfig {

    @Value("${thumbnail.out}")
    private String thumbnailOut;

}
