package com.demo.dddwithmybatis.v1.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Series {
    private Long id;
    private String name;

    private Series(String name)
    {
        this.name = name;
    }

    private Series(Long id, String name) {
        this(name);
        this.id = id;
    }

    public static Series create(String name)
    {
        return new Series(name);
    }

    public static Series create(Long id, String name)
    {
        return new Series(id, name);
    }

    public void update(Series newSeries) {
        if (this.id.equals(newSeries.getId()))
        {
            this.name = newSeries.getName();
        }
    }
}
