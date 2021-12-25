package com.demo.dddwithmybatis.dto.brand;

import com.demo.dddwithmybatis.domain.model.brand.Brand;
import com.demo.dddwithmybatis.dto.series.SeriesResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BrandResponse {
    private Long id;
    private String name;
    private String siteUrl;
    private List<String> brandSynonyms = new ArrayList<>();
    private List<SeriesResponse> seriesResponses = new ArrayList<>();

    public static BrandResponse from(Brand brand) {
        List<SeriesResponse> seriesResponses = brand.getSeriesList().stream()
                .map(SeriesResponse::from)
                .collect(Collectors.toList());
        return new BrandResponse(brand.getId(), brand.getName(), brand.siteUrl(), brand.brandSynonymNames(), seriesResponses);
    }
}
