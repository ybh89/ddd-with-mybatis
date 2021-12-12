package com.demo.dddwithmybatis.v2.infrastructure;

import com.demo.dddwithmybatis.v2.domain.model.Maker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MakerRepositoryV2 {
    int save(@Param("maker") Maker maker);
    int update(@Param("maker") Maker maker);
    Optional<Maker> findById(@Param("id") Long id);
}
