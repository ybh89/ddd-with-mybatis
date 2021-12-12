package com.demo.dddwithmybatis.v2.infrastructure;

import com.demo.dddwithmybatis.v2.domain.model.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandRepositoryV2 {
    int save(@Param("makerId") Long makerId, @Param("brand") Brand brand);
    int update(@Param("brand") Brand brand);
}
