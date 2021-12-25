package com.demo.dddwithmybatis.infrastructure.mapper;

import com.demo.dddwithmybatis.domain.model.maker.Maker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MakerMapper
{
    int save(@Param("maker") Maker maker);
    int update(@Param("maker") Maker maker);
    Optional<Maker> findById(@Param("id") Long id);
}
