package com.demo.dddwithmybatis.dto.series;

import com.demo.dddwithmybatis.domain.model.Series;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
