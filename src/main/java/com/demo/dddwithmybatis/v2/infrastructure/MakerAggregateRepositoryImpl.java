package com.demo.dddwithmybatis.v2.infrastructure;

import com.demo.dddwithmybatis.v2.domain.model.Brand;
import com.demo.dddwithmybatis.v2.domain.model.Maker;
import com.demo.dddwithmybatis.v2.domain.model.Series;
import com.demo.dddwithmybatis.v2.domain.repository.MakerAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
@RequiredArgsConstructor
public class MakerAggregateRepositoryImpl implements MakerAggregateRepository {
    private final MakerRepositoryV2 makerRepository;
    private final BrandRepositoryV2 brandRepository;
    private final SeriesRepositoryV2 seriesRepository;

    @Override
    public Maker save(Maker maker) {
        makerRepository.save(maker);
        maker.getBrands().forEach(brand -> {
            brandRepository.save(maker.getId(), brand);
            brand.getSeriesList().forEach(series -> seriesRepository.save(brand.getId(), series));
        });
        return maker;
    }

    @Override
    public Maker update(Maker originalMaker, Maker newMaker) {
        // dirty checking
        modify(newMaker, originalMaker);
        add(newMaker, originalMaker);
        remove(newMaker, originalMaker);
        return originalMaker;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Maker> findById(Long id) {
        return Optional.ofNullable(makerRepository.findById(id).orElse(null));
    }

    /**
     * 삭제할 시리즈는 originalMaker 에는 존재하지만 newMaker 에서는 존재하지않는다.
     * 해당 시리즈는 삭제 대상이다.
     */
    private void remove(Maker newMaker, Maker originalMaker) {
        originalMaker.getBrands().forEach(originalBrand ->
                newMaker.getBrands().forEach(newBrand -> {
                    if (newBrand.getId().equals(originalBrand.getId())) {
                        List<Series> deleteSeriesList = originalBrand.removeSeries(newBrand);
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
        originalMaker.getBrands().forEach(originalBrand -> newMaker.getBrands().forEach(newBrand -> {
            if (newBrand.getId().equals(originalBrand.getId())) {
                List<Series> addSeriesList = originalBrand.addSeries(newBrand);
                addSeriesList.forEach(addSeries -> seriesRepository.save(originalBrand.getId(), addSeries));
            }
        }));
    }

    /**
     * 수정할 제조사, 브랜드, 시리즈는 newMaker 에서 id(식별자)가 존재한다.
     * 따라서 id가 있으면 수정대상이다.
     */
    private void modify(Maker newMaker, Maker originalMaker) {
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
}
