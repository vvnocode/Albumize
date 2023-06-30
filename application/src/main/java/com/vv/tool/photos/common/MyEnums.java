package com.vv.tool.photos.common;

import lombok.Getter;

public class MyEnums {

    public enum FileType {
        IMAGE(1),
        FOLDER(2),
        ;

        @Getter
        Integer type;

        FileType(Integer type) {
            this.type = type;
        }

    }

    public enum ImgType {
        ORIGINAL(1),
        THUMBNAIL(2),
        ;

        @Getter
        Integer type;

        ImgType(Integer type) {
            this.type = type;
        }

    }

}
