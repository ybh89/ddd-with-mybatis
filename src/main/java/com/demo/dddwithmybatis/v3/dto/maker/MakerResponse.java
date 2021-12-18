package com.demo.dddwithmybatis.v3.dto.maker;

import com.demo.dddwithmybatis.v3.domain.model.Maker;
import com.demo.dddwithmybatis.v3.dto.brand.BrandResponse;
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
