package com.vv.tool.photos.es.element;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "element", createIndex = true)
public class Element {

    @Field(analyzer = "FILE_TYPE", type = FieldType.Integer)
    private Integer fileType;

    @Field(analyzer = "FILE_NAME", type = FieldType.Keyword)
    private String fileName;

    @Field(analyzer = "FILE_PARENT_PATH", type = FieldType.Keyword)
    private String fileParentPath;

    @Id
    @Field(analyzer = "FILE_ABSOLUTE_PATH", type = FieldType.Keyword)
    private String fileAbsolutePath;

    @Field(analyzer = "FILE_SIZE", type = FieldType.Long)
    private Long fileSize;

    @Field(analyzer = "FILE_CREATE_TIME", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date fileCreateTime;

    @Field(analyzer = "THUMBNAIL_ABSOLUTE_PATH", type = FieldType.Text)
    private String thumbnailAbsolutePath;

    @Field(analyzer = "THUMBNAIL_SIZE", type = FieldType.Long)
    private Long thumbnailSize;
}
