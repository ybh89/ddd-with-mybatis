package com.demo.dddwithmybatis.v1.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Series series = (Series) o;
        return Objects.equals(getId(), series.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
