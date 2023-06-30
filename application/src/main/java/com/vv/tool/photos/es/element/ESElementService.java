package com.vv.tool.photos.es.element;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

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
        if (element != null) {
            elementCache.put(id, element);
        }
        return element;
    }
}
