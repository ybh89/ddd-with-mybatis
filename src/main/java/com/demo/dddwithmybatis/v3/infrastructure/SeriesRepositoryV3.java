package com.demo.dddwithmybatis.v3.infrastructure;

import com.demo.dddwithmybatis.v3.domain.model.Series;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeriesRepositoryV3
{
    int save(@Param("brandId") Long brandId, @Param("series") Series series);
    int update(@Param("series") Series series);
    int remove(@Param("series") Series series);
}
