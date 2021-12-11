package com.demo.dddwithmybatis.v1.dto;

import com.demo.dddwithmybatis.v1.domain.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BrandResponse {
    private Long id;
    private String name;
    private List<SeriesResponse> seriesResponses = new ArrayList<>();

    public static BrandResponse from(Brand brand) {
        List<SeriesResponse> seriesResponses = brand.getSeriesList().stream()
                .map(SeriesResponse::from)
                .collect(Collectors.toList());
        return new BrandResponse(brand.getId(), brand.getName(), seriesResponses);
    }
}
