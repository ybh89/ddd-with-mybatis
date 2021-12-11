package com.demo.dddwithmybatis.v1.application;

import com.demo.dddwithmybatis.v1.domain.model.Brand;
import com.demo.dddwithmybatis.v1.domain.model.Maker;
import com.demo.dddwithmybatis.v1.domain.model.Series;
import com.demo.dddwithmybatis.v1.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.v1.dto.maker.MakerUpdateRequest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MakerAggregateFactory {
    private MakerAggregateFactory() {
    }

    public static Maker from(MakerSaveRequest makerSaveRequest)
    {
        List<Brand> brands = makerSaveRequest.getBrandSaveRequests().stream()
                .map(brandRequest -> {
                    if (Objects.isNull(brandRequest.getSeriesRequests()))
                    {
                        brandRequest.setSeriesRequests(Collections.emptyList());
                    }
                    List<Series> seriesList = brandRequest.getSeriesRequests().stream()
                            .map(seriesRequest -> Series.create(seriesRequest.getName()))
                            .collect(Collectors.toList());
                    return Brand.create(brandRequest.getName(), seriesList);
                }).collect(Collectors.toList());
        return Maker.create(makerSaveRequest.getName(), brands);
    }

    public static Maker from(MakerUpdateRequest makerUpdateRequest)
    {
        List<Brand> brands = makerUpdateRequest.getBrandUpdateRequests().stream()
                .map(brandRequest -> {
                    if (Objects.isNull(brandRequest.getSeriesUpdateRequests()))
                    {
                        brandRequest.setSeriesUpdateRequests(Collections.emptyList());
                    }
                    List<Series> seriesList = brandRequest.getSeriesUpdateRequests().stream()
                            .map(seriesRequest -> Series.create(seriesRequest.getId(), seriesRequest.getName()))
                            .collect(Collectors.toList());
                    return Brand.create(brandRequest.getId(), brandRequest.getName(), seriesList);
                }).collect(Collectors.toList());
        return Maker.create(makerUpdateRequest.getId(), makerUpdateRequest.getName(), brands);
    }
}
