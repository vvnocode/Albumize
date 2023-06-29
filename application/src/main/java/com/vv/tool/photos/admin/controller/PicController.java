package com.vv.tool.photos.admin.controller;

import com.alibaba.fastjson.JSON;
import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.es.element.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pic")
public class PicController {

    @Autowired
    private ESElementService esElementService;

    @GetMapping("l")
    public String l(String parentId, Integer pageNo, Integer pageSize) {
        Page<Element> page = esElementService.findByParent(parentId, pageNo, pageSize);
        return JSON.toJSONString(page);
    }

}
