package com.demo.dddwithmybatis.v1.application;

import com.demo.dddwithmybatis.v1.dto.BrandRequest;
import com.demo.dddwithmybatis.v1.dto.MakerRequest;
import com.demo.dddwithmybatis.v1.dto.MakerResponse;
import com.demo.dddwithmybatis.v1.dto.SeriesRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("통합 테스트")
@SpringBootTest
class MakerServiceTest {
    @Autowired
    private MakerService makerService;

    @Test
    void create() {
        // given
        SeriesRequest seriesRequest = new SeriesRequest("시리즈");
        BrandRequest brandRequest = new BrandRequest("브랜드", List.of(seriesRequest));
        MakerRequest makerRequest = new MakerRequest("제조사", List.of(brandRequest));

        // when
        Long makerId = makerService.create(makerRequest);

        // then
        assertThat(makerId).isNotNull();
    }

    @Test
    void retrieve() {
        // given
        SeriesRequest seriesRequest = new SeriesRequest("시리즈");
        BrandRequest brandRequest = new BrandRequest("브랜드", List.of(seriesRequest));
        MakerRequest makerRequest = new MakerRequest("제조사", List.of(brandRequest));
        Long makerId = makerService.create(makerRequest);

        // when
        MakerResponse makerResponse = makerService.retrieve(makerId);

        assertThat(makerResponse.getId()).isNotNull();
        makerResponse.getBrandResponses().forEach(brandResponse -> {
            assertThat(brandResponse.getId()).isNotNull();
            brandResponse.getSeriesResponses().forEach(seriesResponse ->
                    assertThat(seriesResponse.getId()).isNotNull());
        });
    }
}