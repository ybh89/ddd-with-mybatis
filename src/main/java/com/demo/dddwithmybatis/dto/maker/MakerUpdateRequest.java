package com.demo.dddwithmybatis.dto.maker;

import com.demo.dddwithmybatis.dto.brand.BrandUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MakerUpdateRequest {
    private Long id;
    private String name;
    private String siteUrl;
    @Builder.Default
    private List<String> makerSynonyms = new ArrayList<>();
    @Builder.Default
    private List<BrandUpdateRequest> brandUpdateRequests = new ArrayList<>();
}
