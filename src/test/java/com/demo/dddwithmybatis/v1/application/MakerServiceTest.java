package com.demo.dddwithmybatis.v1.application;

import com.demo.dddwithmybatis.v1.dto.BrandRequest;
import com.demo.dddwithmybatis.v1.dto.MakerRequest;
import com.demo.dddwithmybatis.v1.dto.SeriesRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("통합 테스트")
@SpringBootTest
class MakerServiceTest {
    @Autowired
    private MakerService makerService;

    @Test
    void create() {
        // given
        SeriesRequest seriesRequest = new SeriesRequest("시리즈");
        BrandRequest brandRequest = new BrandRequest("브랜드", Arrays.asList(seriesRequest));
        MakerRequest makerRequest = new MakerRequest("제조사", Arrays.asList(brandRequest));

        // when
        Long makerId = makerService.create(makerRequest);
        System.out.println("makerId: " + makerId);

        // then
        assertThat(makerId).isNotNull();
    }
}