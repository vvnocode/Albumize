package com.vv.tool.photos.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePage<T> implements Serializable {

    private Long total;

    private List<T> list;
}
