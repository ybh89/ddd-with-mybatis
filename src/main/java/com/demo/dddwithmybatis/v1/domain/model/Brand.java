package com.demo.dddwithmybatis.v1.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
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

    private Brand(Long id, String name, List<Series> seriesList) {
        this(name, seriesList);
        this.id = id;
    }

    public static Brand create(String name, List<Series> seriesList)
    {
        return new Brand(name, seriesList);
    }

    public static Brand create(Long id, String name, List<Series> seriesList)
    {
        return new Brand(id, name, seriesList);
    }

    public void update(Brand newBrand) {
        if (this.id.equals(newBrand.getId()))
        {
            this.name = newBrand.getName();
        }
    }
}
