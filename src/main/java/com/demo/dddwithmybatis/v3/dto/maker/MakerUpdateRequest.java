package com.demo.dddwithmybatis.v3.dto.maker;

import com.demo.dddwithmybatis.v3.dto.brand.BrandUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MakerUpdateRequest {
    private Long id;
    private String name;
    private String siteUrl;
    private List<String> makerSynonyms = new ArrayList<>();
    private List<BrandUpdateRequest> brandUpdateRequests = new ArrayList<>();
}
