package com.vv.tool.photos.cache;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author vv
 * @date 2023/6/20 15:45
 */
@Configuration
@Getter
public class ScanCache {

    private AtomicLong count = new AtomicLong();
    private AtomicLong countJob = new AtomicLong();
}
