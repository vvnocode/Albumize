package com.vv.tool.photos.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 15:45
 */
public class ScanCache {

    private static ConcurrentHashMap<String, String> taskMap = new ConcurrentHashMap();

    public static void putId(String key, String id) {
        taskMap.put(key, id);
    }

    public static String getId(String key) {
        return taskMap.get(key);
    }
}
