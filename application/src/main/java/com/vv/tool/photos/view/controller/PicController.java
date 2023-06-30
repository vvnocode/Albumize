package com.vv.tool.photos.view.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.vv.tool.photos.base.BasePage;
import com.vv.tool.photos.base.BaseResult;
import com.vv.tool.photos.common.MyEnums;
import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.es.element.Element;
import com.vv.tool.photos.view.mapper.FileMapper;
import com.vv.tool.photos.view.vo.FileVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("pic")
public class PicController {

    @Autowired
    private ESElementService esElementService;

    @Resource
    private FileMapper fileMapper;

    @Autowired
    private Cache<String, Element> elementCache;

    /**
     * 分页查询文件
     *
     * @param parentId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("l")
    public BaseResult<BasePage<FileVo>> l(String parentId, Integer pageNo, Integer pageSize) {
        Page<Element> page = esElementService.findByParent(parentId, pageNo, pageSize);
        List<FileVo> voList = page.getContent().stream().map(o -> fileMapper.entity2Vo(o)).collect(Collectors.toList());
        BasePage<FileVo> basePage = new BasePage<>(page.getTotalElements(), voList);
        return BaseResult.success(0, null, basePage);
    }

    /**
     * 时间线
     *
     * @param sort
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("timeLine")
    public BaseResult<BasePage<FileVo>> l(Sort.Direction sort, Integer pageNo, Integer pageSize) {
        Page<Element> page = esElementService.orderByCreateTime(sort, pageNo, pageSize);
        List<FileVo> voList = page.getContent().stream().map(o -> fileMapper.entity2Vo(o)).collect(Collectors.toList());
        BasePage<FileVo> basePage = new BasePage<>(page.getTotalElements(), voList);
        return BaseResult.success(0, null, basePage);
    }

    /**
     * 访问图片
     *
     * @param id
     * @param type
     * @param response
     * @see com.vv.tool.photos.common.MyEnums
     */
    @GetMapping("p")
    public void p(String id, Integer type, HttpServletResponse response) {
        String filePath;
        Element element = elementCache.asMap().get(id);
        if (element == null) {
            element = esElementService.getById(id);
        }
        //element == null,参数有问题，文件不存在
        if (element == null) {
            log.warn("id = {} , type = {} : 数据不存在", id, type);
            return;
        }
        if (!MyEnums.FileType.IMAGE.getType().equals(element.getFileType())) {
            log.warn("id = {} , type = {} : 图片才有缩略图", id, type);
            return;
        }
        if (MyEnums.ImgType.THUMBNAIL.getType().equals(type)) {
            filePath = element.getThumbnailAbsolutePath();
        } else {
            filePath = element.getFileAbsolutePath();
        }
        if (!ObjectUtils.isEmpty(filePath)) {
            try {
                Files.copy(new File(filePath).toPath(), response.getOutputStream());
            } catch (IOException e) {
                log.warn("id = {} , type = {} : 加载图片出错", id, type);
            }
        }
    }

}
