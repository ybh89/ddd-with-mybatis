package com.demo.dddwithmybatis.v1.dto.maker;

import com.demo.dddwithmybatis.v1.domain.model.Maker;
import com.demo.dddwithmybatis.v1.dto.brand.BrandResponse;
import com.demo.dddwithmybatis.v1.dto.series.SeriesResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MakerResponse {
    private Long id;
    private String name;
    private List<BrandResponse> brandResponses = new ArrayList<>();

    public static MakerResponse from(Maker maker) {
        List<BrandResponse> brandResponses = maker.getBrands().stream()
                .map(BrandResponse::from)
                .collect(Collectors.toList());
        return new MakerResponse(maker.getId(), maker.getName(), brandResponses);
    }
}
