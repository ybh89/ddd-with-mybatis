package com.demo.dddwithmybatis.infrastructure.mapper;

import com.demo.dddwithmybatis.domain.model.brand.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandMapper
{
    int save(@Param("makerId") Long makerId, @Param("brand") Brand brand);
    int update(@Param("brand") Brand brand);
}
