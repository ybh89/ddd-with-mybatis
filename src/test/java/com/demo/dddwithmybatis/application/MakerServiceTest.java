package com.demo.dddwithmybatis.application;

import com.demo.dddwithmybatis.dto.brand.BrandSaveRequest;
import com.demo.dddwithmybatis.dto.brand.BrandUpdateRequest;
import com.demo.dddwithmybatis.dto.maker.MakerResponse;
import com.demo.dddwithmybatis.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.dto.maker.MakerUpdateRequest;
import com.demo.dddwithmybatis.dto.series.SeriesSaveRequest;
import com.demo.dddwithmybatis.dto.series.SeriesUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MakerServiceTest
{
    @Autowired
    private MakerService makerService;

    private MakerResponse initMakerResponse;

    @BeforeEach
    void setUp()
    {
        SeriesSaveRequest seriesRequest = new SeriesSaveRequest("시리즈");
        BrandSaveRequest brandSaveRequest = BrandSaveRequest.builder()
            .name("브랜드")
            .brandSynonyms(Arrays.asList("브랜드동의어1", "브랜드동의어2"))
            .seriesRequests(Collections.singletonList(seriesRequest))
            .build();
        MakerSaveRequest makerSaveRequest = MakerSaveRequest.builder()
            .name("제조사")
            .makerSynonyms(Arrays.asList("제조사동의어1", "제조사동의어2"))
            .brandSaveRequests(Collections.singletonList(brandSaveRequest))
            .build();
        initMakerResponse = makerService.create(makerSaveRequest);
    }

    @DisplayName("제조사 생성 성공")
    @Test
    void create() {
        // given
        // when
        // then
        assertThat(initMakerResponse.getId()).isNotNull();
    }

    @DisplayName("제조사 조회 성공")
    @Test
    void retrieve() {
        // given
        // when
        MakerResponse makerResponse = makerService.retrieve(initMakerResponse.getId());

        // then
        assertThat(makerResponse.getId()).isNotNull();
        makerResponse.getBrandResponses().forEach(brandResponse -> {
            assertThat(brandResponse.getId()).isNotNull();
            brandResponse.getSeriesResponses().forEach(seriesResponse ->
                    assertThat(seriesResponse.getId()).isNotNull());
        });
    }

    @DisplayName("제조사, 브랜드, 시리즈 수정 성공")
    @Test
    public void update() throws Exception {
        // given
        MakerResponse makerResponse = makerService.retrieve(initMakerResponse.getId());

        // when
        List<BrandUpdateRequest> brandUpdateRequests = makerResponse.getBrandResponses().stream()
                .map(brandResponse -> {
                    List<SeriesUpdateRequest> seriesUpdateRequests = brandResponse.getSeriesResponses().stream()
                            .map(seriesResponse -> new SeriesUpdateRequest(seriesResponse.getId(), "수정시리즈"))
                            .collect(Collectors.toList());
                    return BrandUpdateRequest.builder()
                        .id(brandResponse.getId())
                        .name("수정브랜드")
                        .seriesUpdateRequests(seriesUpdateRequests)
                        .build();
                }).collect(Collectors.toList());
        MakerUpdateRequest makerUpdateRequest = MakerUpdateRequest.builder()
            .id(makerResponse.getId())
            .name("수정제조사")
            .brandUpdateRequests(brandUpdateRequests)
            .build();
        MakerResponse updateMakerResponse = makerService.update(makerUpdateRequest);

        // then
        MakerResponse updatedMakerResponse = makerService.retrieve(updateMakerResponse.getId());
        assertThat(updatedMakerResponse.getName()).isEqualTo("수정제조사");
        updatedMakerResponse.getBrandResponses().forEach(brandResponse -> {
            assertThat(brandResponse.getName()).isEqualTo("수정브랜드");
            brandResponse.getSeriesResponses()
                    .forEach(seriesResponse -> assertThat(seriesResponse.getName()).isEqualTo("수정시리즈"));
        });
    }

    @DisplayName("브랜드, 시리즈 추가 성공")
    @Test
    public void add() throws Exception {
        // given
        MakerResponse makerResponse = makerService.retrieve(initMakerResponse.getId());

        // when
        List<BrandUpdateRequest> brandUpdateRequests = makerResponse.getBrandResponses().stream()
                .map(brandResponse -> {
                    List<SeriesUpdateRequest> seriesUpdateRequests = brandResponse.getSeriesResponses().stream()
                            .map(seriesResponse -> new SeriesUpdateRequest(null, "추가시리즈"))
                            .collect(Collectors.toList());
                    return BrandUpdateRequest.builder()
                        .name("추가브랜드")
                        .seriesUpdateRequests(seriesUpdateRequests)
                        .build();
                }).collect(Collectors.toList());
        MakerUpdateRequest makerUpdateRequest = MakerUpdateRequest.builder()
            .id(makerResponse.getId())
            .name("수정제조사")
            .brandUpdateRequests(brandUpdateRequests)
            .build();
        MakerResponse updateMakerResponse = makerService.update(makerUpdateRequest);

        // then
        MakerResponse updatedMakerResponse = makerService.retrieve(updateMakerResponse.getId());
        assertThat(updatedMakerResponse.getName()).isEqualTo("수정제조사");

        assertThat(updatedMakerResponse.getBrandResponses().stream()
                .map(brandResponse -> brandResponse.getName())
                .collect(Collectors.toList())).containsExactly("브랜드", "추가브랜드");

        assertThat(updatedMakerResponse.getBrandResponses().stream()
                .flatMap(brandResponse -> brandResponse.getSeriesResponses().stream())
                .map(seriesResponse -> seriesResponse.getName())
                .collect(Collectors.toList())).containsExactly("시리즈", "추가시리즈");
    }

    @DisplayName("시리즈 삭제 성공")
    @Test
    public void remove() throws Exception {
        // given
        MakerResponse makerResponse = makerService.retrieve(initMakerResponse.getId());

        // when
        List<BrandUpdateRequest> brandUpdateRequests = makerResponse.getBrandResponses().stream()
                .map(brandResponse -> BrandUpdateRequest.builder()
                    .id(makerResponse.getId())
                    .name("수정브랜드")
                    .build())
                .collect(Collectors.toList());
        MakerUpdateRequest makerUpdateRequest = MakerUpdateRequest.builder()
            .id(makerResponse.getId())
            .name("수정제조사")
            .brandUpdateRequests(brandUpdateRequests)
            .build();
        MakerResponse updateMakerResponse = makerService.update(makerUpdateRequest);

        // then
        MakerResponse updatedMakerResponse = makerService.retrieve(updateMakerResponse.getId());
        assertThat(updatedMakerResponse.getName()).isEqualTo("수정제조사");

        assertThat(updatedMakerResponse.getBrandResponses().stream()
                .map(brandResponse -> brandResponse.getName())
                .collect(Collectors.toList())).containsExactly("수정브랜드");

        assertThat(updatedMakerResponse.getBrandResponses().stream()
                .flatMap(brandResponse -> brandResponse.getSeriesResponses().stream())
                .map(seriesResponse -> seriesResponse.getName())
                .collect(Collectors.toList())).isEmpty();
    }

    @DisplayName("제조사 수정, 브랜드 추가, 리소스 추가 삭제")
    @Test
    public void update2() throws Exception {
        //given
        MakerResponse makerResponse = makerService.retrieve(initMakerResponse.getId());

        //when
        List<BrandUpdateRequest> brandUpdateRequests = makerResponse.getBrandResponses().stream()
                .map(brandResponse -> BrandUpdateRequest.builder()
                    .id(makerResponse.getId())
                    .name("브랜드")
                    .build())
                .collect(Collectors.toList());
        brandUpdateRequests.add(BrandUpdateRequest.builder()
                .name("추가브랜드")
                .seriesUpdateRequests(Collections.singletonList(new SeriesUpdateRequest(null, "추가시리즈")))
            .build());
        MakerUpdateRequest makerUpdateRequest = MakerUpdateRequest.builder()
            .id(makerResponse.getId())
            .name("수정제조사")
            .brandUpdateRequests(brandUpdateRequests)
            .build();
        MakerResponse updateMakerResponse = makerService.update(makerUpdateRequest);

        //then
        MakerResponse updatedMakerResponse = makerService.retrieve(updateMakerResponse.getId());
        assertThat(updatedMakerResponse.getName()).isEqualTo("수정제조사");
        assertThat(updatedMakerResponse.getBrandResponses().get(0).getSeriesResponses()).isEmpty();
        assertThat(updatedMakerResponse.getBrandResponses().get(1).getName()).isEqualTo("추가브랜드");
        assertThat(updatedMakerResponse.getBrandResponses().get(1).getSeriesResponses().get(0).getName()).isEqualTo("추가시리즈");
    }
}
