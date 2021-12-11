package com.demo.dddwithmybatis.v1.domain.repository;

import com.demo.dddwithmybatis.v1.domain.model.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandRepository {
    int save(@Param("makerId") Long makerId, @Param("brand") Brand brand);
    int update(@Param("brand") Brand brand);
}
