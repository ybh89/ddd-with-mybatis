package com.demo.dddwithmybatis.v1.domain.repository;

import com.demo.dddwithmybatis.v1.domain.model.Series;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeriesRepository {
    int save(@Param("brandId") Long brandId, @Param("series") Series series);
    int update(@Param("series") Series series);
    int remove(@Param("series") Series series);
}
