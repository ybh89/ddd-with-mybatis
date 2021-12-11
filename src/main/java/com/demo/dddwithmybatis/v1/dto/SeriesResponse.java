package com.demo.dddwithmybatis.v1.dto;

import com.demo.dddwithmybatis.v1.domain.model.Series;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SeriesResponse {
    private Long id;
    private String name;

    public static SeriesResponse from(Series series)
    {
        return new SeriesResponse(series.getId(), series.getName());
    }
}
