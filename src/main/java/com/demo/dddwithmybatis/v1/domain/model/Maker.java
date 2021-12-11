package com.demo.dddwithmybatis.v1.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
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

    private Maker(Long id, String name, List<Brand> brands)
    {
        this(name, brands);
        this.id = id;
    }

    public static Maker create(String name, List<Brand> brands)
    {
        return new Maker(name, brands);
    }

    public static Maker create(Long id, String name, List<Brand> brands)
    {
        return new Maker(id, name, brands);
    }

    public void update(Maker newMaker) {
        if (this.id.equals(newMaker.getId()))
        {
            this.name = newMaker.getName();
        }
    }
}
