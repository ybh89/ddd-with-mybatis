package com.demo.dddwithmybatis.v1.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Brand {
    private Long id;
    private String name;

    private List<Series> seriesList = new ArrayList<>();

    private Brand(String name, List<Series> seriesList) {
        this.name = name;
        this.seriesList = seriesList;
    }

    public static Brand create(String name, List<Series> seriesList)
    {
        return new Brand(name, seriesList);
    }
}
