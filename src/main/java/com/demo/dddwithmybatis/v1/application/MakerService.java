package com.demo.dddwithmybatis.v1.application;

import com.demo.dddwithmybatis.v1.domain.model.Brand;
import com.demo.dddwithmybatis.v1.domain.model.Maker;
import com.demo.dddwithmybatis.v1.domain.model.Series;
import com.demo.dddwithmybatis.v1.domain.repository.BrandRepository;
import com.demo.dddwithmybatis.v1.domain.repository.MakerRepository;
import com.demo.dddwithmybatis.v1.domain.repository.SeriesRepository;
import com.demo.dddwithmybatis.v1.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.v1.dto.maker.MakerResponse;
import com.demo.dddwithmybatis.v1.dto.maker.MakerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MakerService {
    private final MakerRepository makerRepository;
    private final BrandRepository brandRepository;
    private final SeriesRepository seriesRepository;

    @Transactional
    public Long create(MakerSaveRequest makerSaveRequest) {
        Maker maker = MakerAggregateFactory.from(makerSaveRequest);
        makerRepository.save(maker);
        maker.getBrands().forEach(brand -> {
            brandRepository.save(maker.getId(), brand);
            brand.getSeriesList().forEach(series -> seriesRepository.save(brand.getId(), series));
        });
        return maker.getId();
    }

    @Transactional
    public Long update(MakerUpdateRequest makerUpdateRequest) {
        Maker newMaker = MakerAggregateFactory.from(makerUpdateRequest);
        Maker originalMaker = findById(makerUpdateRequest.getId());

        update(newMaker, originalMaker);
        add(newMaker, originalMaker);
        remove(newMaker, originalMaker);
        return originalMaker.getId();
    }

    /**
     * 삭제할 시리즈는 originalMaker 에는 존재하지만 newMaker 에서는 존재하지않는다.
     * 해당 시리즈는 삭제 대상이다.
     */
    private void remove(Maker newMaker, Maker originalMaker) {
        originalMaker.getBrands().forEach(originalBrand ->
                newMaker.getBrands().forEach(newBrand -> {
                    if (newBrand.getId().equals(originalBrand.getId())) {
                        List<Series> deleteSeriesList = originalBrand.deleteSeries(newBrand);
                        deleteSeriesList.forEach(series -> seriesRepository.remove(series));
                    }
                }));
    }

    /**
     * 추가할 브랜드, 시리즈는 newMaker 에서 id(식별자)가 존재하지않는다.
     * 따라서 id가 없으면 추가대상이다.
     */
    private void add(Maker newMaker, Maker originalMaker) {
        List<Brand> addBrands = originalMaker.addBrands(newMaker);
        addBrands.forEach(addBrand -> brandRepository.save(originalMaker.getId(), addBrand));
        originalMaker.getBrands().forEach(originalBrand -> {
            newMaker.getBrands().forEach(newBrand -> {
                if (newBrand.getId().equals(originalBrand.getId())) {
                    List<Series> addSeriesList = originalBrand.addSeries(newBrand);
                    addSeriesList.forEach(addSeries -> seriesRepository.save(originalBrand.getId(), addSeries));
                }
            });
        });
    }

    /**
     * 수정할 제조사, 브랜드, 시리즈는 newMaker 에서 id(식별자)가 존재한다.
     * 따라서 id가 있으면 수정대상이다.
     */
    private void update(Maker newMaker, Maker originalMaker) {
        originalMaker.update(newMaker);
        makerRepository.update(originalMaker);
        originalMaker.getBrands().forEach(originalBrand ->
                newMaker.getBrands().forEach(newBrand -> {
                    if (!Objects.isNull(newBrand.getId()) && newBrand.getId().equals(originalBrand.getId())) {
                        originalBrand.update(newBrand);
                        brandRepository.update(originalBrand);
                        originalBrand.getSeriesList().forEach(originalSeries ->
                                newBrand.getSeriesList().forEach(newSeries -> {
                                    if (!Objects.isNull(newSeries.getId()) && newSeries.getId().equals(originalSeries.getId())) {
                                        originalSeries.update(newSeries);
                                        seriesRepository.update(originalSeries);
                                    }
                                }));
                    }
                }));
    }

    @Transactional(readOnly = true)
    public MakerResponse retrieve(Long makerId) {
        Maker maker = findById(makerId);
        System.out.println("maker= " + maker);
        return MakerResponse.from(maker);
    }

    private Maker findById(Long makerId) {
        return makerRepository.findById(makerId)
                .orElseThrow(() -> new IllegalStateException("제조사를 찾을 수 없습니다."));
    }
}
