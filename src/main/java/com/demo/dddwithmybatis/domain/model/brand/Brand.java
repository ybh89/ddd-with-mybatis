package com.demo.dddwithmybatis.domain.model.brand;

import com.demo.dddwithmybatis.domain.Entity;
import com.demo.dddwithmybatis.domain.model.Series;
import com.demo.dddwithmybatis.domain.model.URL;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Brand {
    private Long id;
    private String name;
    private URL siteUrl;
    private List<BrandSynonym> brandSynonyms;

    private List<Series> seriesList = new ArrayList<>();

    private Brand(Long id, String name, URL siteUrl, List<Series> seriesList, List<BrandSynonym> brandSynonyms) {
        this.name = name;
        this.siteUrl = siteUrl;
        this.brandSynonyms = brandSynonyms;
        this.seriesList = seriesList;
        this.id = id;
    }

    public static Brand create(Long id, String name, List<String> brandSynonymNames, String siteUrl, List<Series> seriesList)
    {
        if (Objects.isNull(brandSynonymNames))
        {
            brandSynonymNames = Collections.emptyList();
        }
        List<BrandSynonym> brandSynonyms = brandSynonymNames.stream()
            .map(BrandSynonym::new)
            .collect(Collectors.toList());
        return new Brand(id, name, new URL(siteUrl), seriesList, brandSynonyms);
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

    public String siteUrl()
    {
        return this.siteUrl.value();
    }

    public List<String> brandSynonymNames()
    {
        return brandSynonyms.stream()
            .map(BrandSynonym::value)
            .collect(Collectors.toList());
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
