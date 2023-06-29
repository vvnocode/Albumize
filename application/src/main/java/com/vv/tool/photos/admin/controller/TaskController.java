package com.vv.tool.photos.admin.controller;

import com.vv.tool.photos.config.PropertiesConfig;
import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.job.ScanJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 14:26
 */
@Slf4j
@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private ESElementService esElementService;

    @Autowired
    private ThreadPoolTaskExecutor scanExecutor;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private ThreadPoolTaskExecutor compressExecutor;

    @GetMapping("scan")
    public String scan() {
        scanExecutor.execute(new ScanJob(propertiesConfig, compressExecutor, esElementService));
        return "success";
    }
}
