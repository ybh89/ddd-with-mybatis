package com.demo.dddwithmybatis.domain.model.maker;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class MakerSynonym
{
    private String name;

    public MakerSynonym(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MakerSynonym that = (MakerSynonym) o;
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
