package com.demo.dddwithmybatis.v2.infrastructure;

import com.demo.dddwithmybatis.v2.domain.model.Series;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeriesRepositoryV2 {
    int save(@Param("brandId") Long brandId, @Param("series") Series series);
    int update(@Param("series") Series series);
    int remove(@Param("series") Series series);
}
