package com.demo.dddwithmybatis.dto.maker;

import com.demo.dddwithmybatis.domain.model.maker.Maker;
import com.demo.dddwithmybatis.dto.brand.BrandResponse;
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
    private String siteUrl;
    private List<String> makerSynonyms = new ArrayList<>();
    private List<BrandResponse> brandResponses = new ArrayList<>();

    public static MakerResponse from(Maker maker) {
        List<BrandResponse> brandResponses = maker.getBrands().stream()
                .map(BrandResponse::from)
                .collect(Collectors.toList());
        return new MakerResponse(maker.getId(), maker.getName(), maker.siteUrl(), maker.makerSynonymNames(), brandResponses);
    }
}
