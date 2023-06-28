package com.vv.tool.photos.cache;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 15:45
 */
@Getter
public class ScanCache {

    public static AtomicLong count = new AtomicLong();
    public static AtomicLong countJob = new AtomicLong();

    public static ConcurrentHashMap<String, Object> taskMap = new ConcurrentHashMap();
}
