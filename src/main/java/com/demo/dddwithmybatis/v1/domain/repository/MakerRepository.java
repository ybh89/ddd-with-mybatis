package com.demo.dddwithmybatis.v1.domain.repository;

import com.demo.dddwithmybatis.v1.domain.model.Maker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MakerRepository {
    int save(@Param("maker") Maker maker);
    int update(@Param("maker") Maker maker);
    Optional<Maker> findById(@Param("id") Long id);
}
