package com.demo.dddwithmybatis.dto.maker;

import com.demo.dddwithmybatis.dto.brand.BrandSaveRequest;
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
public class MakerSaveRequest {
    private String name;
    private String siteUrl;
    @Builder.Default
    private List<String> makerSynonyms = new ArrayList<>();
    @Builder.Default
    private List<BrandSaveRequest> brandSaveRequests = new ArrayList<>();
}
