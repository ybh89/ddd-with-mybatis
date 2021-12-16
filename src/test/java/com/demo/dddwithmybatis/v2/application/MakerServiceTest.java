package com.demo.dddwithmybatis.v2.application;

import com.demo.dddwithmybatis.v2.dto.brand.BrandSaveRequest;
import com.demo.dddwithmybatis.v2.dto.brand.BrandUpdateRequest;
import com.demo.dddwithmybatis.v2.dto.maker.MakerResponse;
import com.demo.dddwithmybatis.v2.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.v2.dto.maker.MakerUpdateRequest;
import com.demo.dddwithmybatis.v2.dto.series.SeriesSaveRequest;
import com.demo.dddwithmybatis.v2.dto.series.SeriesUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("V2")
@SpringBootTest
public class MakerServiceTest {
    @Autowired
    private MakerServiceV2 makerService;

    private MakerResponse initMakerResponse;

    @BeforeEach
    void setUp()
    {
        SeriesSaveRequest seriesRequest = new SeriesSaveRequest("시리즈");
        BrandSaveRequest brandSaveRequest = new BrandSaveRequest("브랜드", Collections.singletonList(seriesRequest));
        MakerSaveRequest makerSaveRequest = new MakerSaveRequest("제조사", Collections.singletonList(brandSaveRequest));
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
                    return new BrandUpdateRequest(brandResponse.getId(), "수정브랜드", seriesUpdateRequests);
                }).collect(Collectors.toList());
        MakerUpdateRequest makerUpdateRequest = new MakerUpdateRequest(makerResponse.getId(), "수정제조사", brandUpdateRequests);
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
                    return new BrandUpdateRequest(null, "추가브랜드", seriesUpdateRequests);
                }).collect(Collectors.toList());
        MakerUpdateRequest makerUpdateRequest = new MakerUpdateRequest(makerResponse.getId(), "수정제조사", brandUpdateRequests);
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
                .map(brandResponse -> new BrandUpdateRequest(makerResponse.getId(), "수정브랜드", null))
                .collect(Collectors.toList());
        MakerUpdateRequest makerUpdateRequest = new MakerUpdateRequest(makerResponse.getId(), "수정제조사", brandUpdateRequests);
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
                .map(brandResponse -> new BrandUpdateRequest(makerResponse.getId(), "브랜드", null))
                .collect(Collectors.toList());
        brandUpdateRequests.add(new BrandUpdateRequest(null, "추가브랜드", Collections.singletonList(new SeriesUpdateRequest(null, "추가시리즈"))));
        MakerUpdateRequest makerUpdateRequest = new MakerUpdateRequest(makerResponse.getId(), "수정제조사", brandUpdateRequests);
        MakerResponse updateMakerResponse = makerService.update(makerUpdateRequest);

        //then
        MakerResponse updatedMakerResponse = makerService.retrieve(updateMakerResponse.getId());
        assertThat(updatedMakerResponse.getName()).isEqualTo("수정제조사");
        assertThat(updatedMakerResponse.getBrandResponses().get(0).getSeriesResponses()).isEmpty();
        assertThat(updatedMakerResponse.getBrandResponses().get(1).getName()).isEqualTo("추가브랜드");
        assertThat(updatedMakerResponse.getBrandResponses().get(1).getSeriesResponses().get(0).getName()).isEqualTo("추가시리즈");
    }
}
