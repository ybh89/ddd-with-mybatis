package com.demo.dddwithmybatis.dto.brand;

import com.demo.dddwithmybatis.dto.series.SeriesUpdateRequest;
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
public class BrandUpdateRequest {
    private Long id;
    private String name;
    private String siteUrl;
    @Builder.Default
    private List<String> brandSynonyms = new ArrayList<>();
    @Builder.Default
    private List<SeriesUpdateRequest> seriesUpdateRequests = new ArrayList<>();
}
