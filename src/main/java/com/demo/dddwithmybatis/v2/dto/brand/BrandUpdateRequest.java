package com.demo.dddwithmybatis.v2.dto.brand;

import com.demo.dddwithmybatis.v2.dto.series.SeriesUpdateRequest;
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
public class BrandUpdateRequest {
    private Long id;
    private String name;
    private List<SeriesUpdateRequest> seriesUpdateRequests = new ArrayList<>();
}
