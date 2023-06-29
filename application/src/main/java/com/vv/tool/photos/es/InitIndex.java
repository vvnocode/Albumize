package com.vv.tool.photos.es;


import com.vv.tool.photos.es.element.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 0)
public class InitIndex implements CommandLineRunner {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 项目启动的时候，如果elasticsearch已经存有索引，则不做任何操作
     * 如果没有索引，则新建索引
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        IndexOperations indexOperations = elasticsearchTemplate.indexOps(Element.class);
        boolean indexExists = indexOperations.exists();
        if (indexExists) {
            log.warn("存在索引");
        } else {
            log.warn("索引不存在。。。");
            try {
                boolean index = indexOperations.create();
                boolean putMapping = indexOperations.putMapping();
                if (index && putMapping) {
                    log.info("索引创建成功。。。");
                } else {
                    log.warn("索引创建失败。。。");
                }
            } catch (Exception e) {
                log.error("error: {}", e.getLocalizedMessage());
            }
        }
    }

}
