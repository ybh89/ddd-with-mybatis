package com.demo.dddwithmybatis.v3.infrastructure;

import com.demo.dddwithmybatis.v3.domain.model.Maker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MakerRepositoryV3
{
    int save(@Param("maker") Maker maker);
    int update(@Param("maker") Maker maker);
    Optional<Maker> findById(@Param("id") Long id);
}
