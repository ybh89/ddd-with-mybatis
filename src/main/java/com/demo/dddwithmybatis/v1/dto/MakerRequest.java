package com.demo.dddwithmybatis.v1.dto;

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
public class MakerRequest {
    private String name;
    private List<BrandRequest> brandRequests = new ArrayList<>();
}
