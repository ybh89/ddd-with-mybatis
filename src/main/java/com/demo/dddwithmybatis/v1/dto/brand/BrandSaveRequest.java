package com.demo.dddwithmybatis.v1.dto.brand;

import com.demo.dddwithmybatis.v1.dto.series.SeriesSaveRequest;
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
public class BrandSaveRequest {
    private String name;
    private List<SeriesSaveRequest> seriesRequests = new ArrayList<>();
}
