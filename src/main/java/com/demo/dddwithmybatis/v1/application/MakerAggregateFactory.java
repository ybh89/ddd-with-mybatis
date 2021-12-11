package com.demo.dddwithmybatis.v1.application;

import com.demo.dddwithmybatis.v1.domain.model.Brand;
import com.demo.dddwithmybatis.v1.domain.model.Maker;
import com.demo.dddwithmybatis.v1.domain.model.Series;
import com.demo.dddwithmybatis.v1.dto.MakerRequest;

import java.util.List;
import java.util.stream.Collectors;

public class MakerAggregateFactory {
    private MakerAggregateFactory() {
    }

    public static Maker from(MakerRequest makerRequest) {
        List<Brand> brands = makerRequest.getBrandRequests().stream()
                .map(brandRequest -> {
                    List<Series> seriesList = brandRequest.getSeriesRequests().stream()
                            .map(seriesRequest -> Series.create(seriesRequest.getName()))
                            .collect(Collectors.toList());
                    return Brand.create(brandRequest.getName(), seriesList);
                }).collect(Collectors.toList());
        return Maker.create(makerRequest.getName(), brands);
    }
}
