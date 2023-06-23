package com.vv.tool.photos.es.element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ESElementService {

    @Autowired
    private ESElementRepository esElementRepository;

    public Element Save(Element element) {
        return esElementRepository.save(element);
    }

    public Page<Element> findByParent(String parent, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return esElementRepository.findElementsByFileParentPathEquals(parent, pageable);
    }
}
