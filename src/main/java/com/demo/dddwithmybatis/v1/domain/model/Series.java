package com.demo.dddwithmybatis.v1.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Series {
    private Long id;
    private String name;

    private Series(String name)
    {
        this.name = name;
    }

    public static Series create(String name)
    {
        return new Series(name);
    }
}
