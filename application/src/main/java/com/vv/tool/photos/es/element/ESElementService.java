package com.vv.tool.photos.es.element;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Slf4j
@Service
public class ESElementService {

    @Autowired
    private ESElementRepository esElementRepository;

    @Autowired
    private Cache<String, Element> elementCache;

    public Element Save(Element element) {
        return esElementRepository.save(element);
    }

    public Page<Element> findByParent(String parent, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Element> elementPage = esElementRepository.findAllByParentIdEquals(Objects.equals(parent, "") ? null : parent, pageable);
        //存入缓存
        if (!ObjectUtils.isEmpty(elementPage.getContent())) {
            for (Element element : elementPage.getContent()) {
                elementCache.put(element.getId(), element);
            }
        }
        return elementPage;
    }

    public Element getById(String id) {
        Element element = esElementRepository.findById(id).orElse(null);
        //存入缓存
        if (element != null) {
            elementCache.put(id, element);
        }
        return element;
    }

    /**
     * 时间线接口
     *
     * @param sort
     * @param page
     * @param size
     * @return
     */
    public Page<Element> orderByCreateTime(Sort.Direction sort, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort, "fileCreateTime"));
        try {
            Page<Element> elementPage = esElementRepository.findAll(pageable);
            //存入缓存
            if (!ObjectUtils.isEmpty(elementPage.getContent())) {
                for (Element element : elementPage.getContent()) {
                    elementCache.put(element.getId(), element);
                }
            }
            return elementPage;
        } catch (Exception e) {
            log.error("时间线查询出错 : {}", e.getLocalizedMessage());
        }
        return Page.empty(pageable);
    }

}
