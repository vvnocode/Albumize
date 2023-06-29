package com.vv.tool.photos.view.controller;

import com.vv.tool.photos.base.BasePage;
import com.vv.tool.photos.base.BaseResult;
import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.es.element.Element;
import com.vv.tool.photos.view.mapper.FileMapper;
import com.vv.tool.photos.view.vo.FileVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pic")
public class PicController {

    @Autowired
    private ESElementService esElementService;

    @Resource
    private FileMapper fileMapper;

    @GetMapping("l")
    public BaseResult<BasePage<FileVo>> l(String parentId, Integer pageNo, Integer pageSize) {
        Page<Element> page = esElementService.findByParent(parentId, pageNo, pageSize);
        List<FileVo> voList = page.getContent().stream().map(o -> fileMapper.entity2Vo(o)).collect(Collectors.toList());
        BasePage<FileVo> basePage = new BasePage<>(page.getTotalElements(), voList);
        return BaseResult.success(0, null, basePage);
    }

}
