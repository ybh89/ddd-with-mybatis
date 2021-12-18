package com.demo.dddwithmybatis.v3.dto.maker;

import com.demo.dddwithmybatis.v3.dto.brand.BrandSaveRequest;
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
public class MakerSaveRequest {
    private String name;
    private List<BrandSaveRequest> brandSaveRequests = new ArrayList<>();
}
