package com.demo.dddwithmybatis.infrastructure.mapper;

import com.demo.dddwithmybatis.domain.model.Series;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeriesMapper
{
    int save(@Param("brandId") Long brandId, @Param("series") Series series);
    int update(@Param("series") Series series);
    int remove(@Param("series") Series series);
}
