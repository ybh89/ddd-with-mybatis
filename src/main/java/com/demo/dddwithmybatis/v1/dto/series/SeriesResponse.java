package com.demo.dddwithmybatis.v1.dto.series;

import com.demo.dddwithmybatis.v1.domain.model.Series;
import lombok.*;

@ToString
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
