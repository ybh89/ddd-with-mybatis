package com.demo.dddwithmybatis.v2.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<Series> addSeries(Brand newBrand) {
        List<Series> newSeries = newBrand.getSeriesList().stream()
                .filter(series -> Objects.isNull(series.getId()))
                .collect(Collectors.toList());

        this.seriesList.addAll(newSeries);
        return newSeries;
    }

    public List<Series> removeSeries(Brand newBrand)
    {
        List<Series> removeSeriesList = this.seriesList.stream()
                .filter(series -> !newBrand.getSeriesList().contains(series))
                .collect(Collectors.toList());
        this.seriesList.removeAll(removeSeriesList);
        return removeSeriesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(getId(), brand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
