package com.vv.tool.photos.spring;

import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.es.element.Element;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Date;

@Slf4j
@SpringBootTest
public class TestES {

    @Autowired
    private ESElementService esElementService;

    @Test
    public void testSave() {
        for (int i = 0; i < 10000 * 100; i++) {
            String fileName = "我是文件名称" + i + ".jpg";
            String parentPath = "/cos/王某某/海边";
            Element element = new Element();
            element.setFileName(fileName);
            element.setFileType(1);
            element.setFileSize(1024L * 10);
            element.setThumbnailSize(12L);
            element.setFileParentPath(parentPath);
            element.setFileCreateTime(new Date());
            element.setFileAbsolutePath(parentPath + "/" + fileName);
            element.setThumbnailAbsolutePath("/data/001/9847838" + i + ".jpg");
            Element save = esElementService.Save(element);
            log.debug("保存 {} 条数据：{}", i, save);
        }
    }

    @Test
    public void testFindByPath() {
        Page<Element> page = esElementService.findByParent("/cos/王某某/海边", 10, 10);
        log.debug("查询分页结果：{}", page);
    }
}
