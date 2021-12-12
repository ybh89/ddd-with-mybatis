package com.demo.dddwithmybatis.v2.dto.maker;

import com.demo.dddwithmybatis.v2.domain.model.Maker;
import com.demo.dddwithmybatis.v2.dto.brand.BrandResponse;
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
