package com.demo.dddwithmybatis.application;

import com.demo.dddwithmybatis.domain.model.brand.Brand;
import com.demo.dddwithmybatis.domain.model.maker.Maker;
import com.demo.dddwithmybatis.domain.model.Series;
import com.demo.dddwithmybatis.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.dto.maker.MakerUpdateRequest;

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
                    return Brand.create(null, brandRequest.getName(), brandRequest.getBrandSynonyms()
                        , brandRequest.getSiteUrl(), seriesList);
                }).collect(Collectors.toList());
        return Maker.create(null, makerSaveRequest.getName(), makerSaveRequest.getMakerSynonyms()
            , makerSaveRequest.getSiteUrl(), brands);
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
                    return Brand.create(brandRequest.getId(), brandRequest.getName(), brandRequest.getBrandSynonyms()
                        , brandRequest.getSiteUrl(), seriesList);
                }).collect(Collectors.toList());
        return Maker.create(makerUpdateRequest.getId(), makerUpdateRequest.getName()
            , makerUpdateRequest.getMakerSynonyms(), makerUpdateRequest.getSiteUrl(), brands);
    }
}
