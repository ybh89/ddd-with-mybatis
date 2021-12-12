package com.demo.dddwithmybatis.v2.dto.series;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SeriesUpdateRequest {
    private Long id;
    private String name;
}
