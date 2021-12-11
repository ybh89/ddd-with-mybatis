package com.demo.dddwithmybatis.domain;

import java.util.ArrayList;
import java.util.List;

public class Brand {
    private Long id;
    private String name;

    private List<Series> seriesList = new ArrayList<>();
}
