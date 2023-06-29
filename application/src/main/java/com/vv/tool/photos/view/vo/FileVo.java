package com.vv.tool.photos.view.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FileVo {

    private String id;

    private String parentId;

    private Integer fileType;

    private String fileName;

    private Long fileSize;

    private Date fileCreateTime;

}
