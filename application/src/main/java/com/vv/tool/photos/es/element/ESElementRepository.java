package com.vv.tool.photos.es.element;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESElementRepository extends ElasticsearchRepository<Element, String> {

    Page<Element> findAllByParentIdEquals(String parentId, Pageable pageable);
}
