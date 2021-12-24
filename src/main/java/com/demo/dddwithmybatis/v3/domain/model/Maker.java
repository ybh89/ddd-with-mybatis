package com.demo.dddwithmybatis.v3.domain.model;

import com.demo.dddwithmybatis.v3.domain.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public List<Brand> addBrands(Maker newMaker) {
        List<Brand> newBrands = newMaker.getBrands().stream()
                .filter(brand -> Objects.isNull(brand.getId()))
                .collect(Collectors.toList());

        this.brands.addAll(newBrands);
        return newBrands;
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
