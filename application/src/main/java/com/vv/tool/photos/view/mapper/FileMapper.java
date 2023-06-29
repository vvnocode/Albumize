package com.vv.tool.photos.view.mapper;

import com.vv.tool.photos.es.element.Element;
import com.vv.tool.photos.view.vo.FileVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileVo entity2Vo(Element element);
}
