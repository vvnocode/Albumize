package com.vv.tool.photos.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public static <T> BaseResult<T> success(Integer code, String msg, T t) {
        return new BaseResult<>(code, msg, t);
    }

}
