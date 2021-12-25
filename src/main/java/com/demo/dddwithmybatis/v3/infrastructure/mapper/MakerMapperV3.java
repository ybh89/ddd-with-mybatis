package com.demo.dddwithmybatis.v3.infrastructure.mapper;

import com.demo.dddwithmybatis.v3.domain.model.maker.Maker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MakerMapperV3
{
    int save(@Param("maker") Maker maker);
    int update(@Param("maker") Maker maker);
    Optional<Maker> findById(@Param("id") Long id);
}
