package com.demo.dddwithmybatis.v3.infrastructure.mapper;

import com.demo.dddwithmybatis.v3.domain.model.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandMapperV3
{
    int save(@Param("makerId") Long makerId, @Param("brand") Brand brand);
    int update(@Param("brand") Brand brand);
}
