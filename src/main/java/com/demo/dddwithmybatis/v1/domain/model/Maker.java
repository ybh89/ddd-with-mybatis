package com.demo.dddwithmybatis.v1.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Maker {
    private Long id;
    private String name;
    private List<Brand> brands;

    private Maker(String name, List<Brand> brands)
    {
        this.name = name;
        this.brands = brands;
    }

    public static Maker create(String name, List<Brand> brands)
    {
        return new Maker(name, brands);
    }
}
