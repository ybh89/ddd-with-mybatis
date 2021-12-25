package com.demo.dddwithmybatis.domain.model.maker;

import com.demo.dddwithmybatis.domain.Entity;
import com.demo.dddwithmybatis.domain.model.brand.Brand;
import com.demo.dddwithmybatis.domain.model.URL;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Maker {
    private Long id;
    private String name;
    private List<MakerSynonym> makerSynonyms;
    private URL siteUrl;
    private List<Brand> brands;

    private Maker(Long id, String name, List<Brand> brands, List<MakerSynonym> makerSynonyms, URL siteUrl)
    {
        this.name = name;
        this.makerSynonyms = makerSynonyms;
        this.brands = brands;
        this.id = id;
        this.siteUrl = siteUrl;
    }

    public static Maker create(Long id, String name, List<String> makerSynonymNames, String siteUrl, List<Brand> brands)
    {
        if (Objects.isNull(makerSynonymNames))
        {
            makerSynonymNames = Collections.emptyList();
        }
        List<MakerSynonym> makerSynonyms = makerSynonymNames.stream()
            .map(MakerSynonym::new)
            .collect(Collectors.toList());
        return new Maker(id, name, brands, makerSynonyms, new URL(siteUrl));
    }

    public void update(Maker newMaker) {
        if (this.id.equals(newMaker.getId()))
        {
            this.name = newMaker.getName();
        }
    }

    public List<Brand> addBrands(Maker newMaker) {
        List<Brand> newBrands = newMaker.getBrands().stream()
                .filter(brand -> Objects.isNull(brand.getId()))
                .collect(Collectors.toList());

        this.brands.addAll(newBrands);
        return newBrands;
    }

    public String siteUrl()
    {
        return this.siteUrl.value();
    }

    public List<String> makerSynonymNames()
    {
        return makerSynonyms.stream()
            .map(MakerSynonym::value)
            .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maker maker = (Maker) o;
        return Objects.equals(getId(), maker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
