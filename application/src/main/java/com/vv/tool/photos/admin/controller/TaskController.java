package com.vv.tool.photos.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("start")
    public String start() {
        return "success";
    }
}
