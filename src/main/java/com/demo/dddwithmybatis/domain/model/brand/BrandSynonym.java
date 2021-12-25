package com.demo.dddwithmybatis.domain.model.brand;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class BrandSynonym
{
    private String name;

    public BrandSynonym(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandSynonym that = (BrandSynonym) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    public String value()
    {
        return name;
    }
}
