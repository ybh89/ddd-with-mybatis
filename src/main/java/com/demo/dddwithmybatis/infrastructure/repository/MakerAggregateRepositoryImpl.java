package com.demo.dddwithmybatis.infrastructure.repository;

import com.demo.dddwithmybatis.domain.model.brand.Brand;
import com.demo.dddwithmybatis.domain.model.maker.Maker;
import com.demo.dddwithmybatis.domain.model.Series;
import com.demo.dddwithmybatis.domain.repository.MakerAggregateRepository;
import com.demo.dddwithmybatis.infrastructure.mapper.BrandMapper;
import com.demo.dddwithmybatis.infrastructure.EntityChangeDetector;
import com.demo.dddwithmybatis.infrastructure.mapper.MakerMapper;
import com.demo.dddwithmybatis.infrastructure.mapper.SeriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
@RequiredArgsConstructor
public class MakerAggregateRepositoryImpl implements MakerAggregateRepository
{
    private final MakerMapper makerMapper;
    private final BrandMapper brandMapper;
    private final SeriesMapper seriesMapper;
    private final EntityChangeDetector entityChangeDetector;

    @Override
    public Maker save(Maker maker)
    {
        makerMapper.save(maker);
        makerMapper.saveMakerSynonyms(maker);
        maker.getBrands().forEach(brand ->
        {
            brandMapper.save(maker.getId(), brand);
            brandMapper.saveBrandSynonyms(brand);
            brand.getSeriesList().forEach(series -> seriesMapper.save(brand.getId(), series));
        });
        return maker;
    }

    @Override
    public Maker update(Maker originalMaker, Maker newMaker)
    {
        // dirty checking
        modify(newMaker, originalMaker);
        add(newMaker, originalMaker);
        remove(newMaker, originalMaker);
        change(newMaker, originalMaker);
        return originalMaker;
    }

    /**
     * 값 타입 컬렉션인 제조사 동의어, 브랜드 동의어는 테이블이 따로 존재하므로 전체 delete 후, 전체 insert 를 한다.
     */
    private void change(Maker newMaker, Maker originalMaker)
    {
        originalMaker.changeMakerSynonyms(newMaker);
        makerMapper.removeMakerSynonyms(originalMaker);
        makerMapper.saveMakerSynonyms(originalMaker);

        for (Brand originalBrand : originalMaker.getBrands())
        {
            newMaker.getBrands().stream()
                    .filter(newBrand -> originalBrand.getId().equals(newBrand.getId()))
                        .forEach(brand -> {
                            originalBrand.changeBrandSynonyms(brand);
                            brandMapper.removeBrandSynonyms(originalBrand);
                            brandMapper.saveBrandSynonyms(originalBrand);
                        });
        }
    }

    @Override
    public Optional<Maker> findById(Long id)
    {
        return makerMapper.findById(id);
    }

    /**
     * 삭제할 시리즈는 originalMaker 에는 존재하지만 newMaker 에서는 존재하지않는다.
     * 해당 시리즈는 삭제 대상이다.
     */
    private void remove(Maker newMaker, Maker originalMaker)
    {
        originalMaker.getBrands().forEach(originalBrand ->
            newMaker.getBrands().forEach(newBrand ->
            {
                if (newBrand.getId().equals(originalBrand.getId()))
                {
                    List<Series> deleteSeriesList = originalBrand.removeSeries(newBrand);
                    deleteSeriesList.forEach(seriesMapper::remove);
                }
            }));
    }

    /**
     * 추가할 브랜드, 시리즈는 newMaker 에서 id(식별자)가 존재하지않는다.
     * 따라서 id가 없으면 추가대상이다.
     */
    private void add(Maker newMaker, Maker originalMaker)
    {
        List<Brand> addBrands = originalMaker.addBrands(newMaker);
        addBrands.forEach(addBrand -> {
            brandMapper.save(originalMaker.getId(), addBrand);
            addBrand.getSeriesList().forEach(series -> seriesMapper.save(addBrand.getId(), series));
        });

        originalMaker.getBrands().forEach(originalBrand -> newMaker.getBrands().forEach(newBrand ->
        {
            if (newBrand.getId().equals(originalBrand.getId()))
            {
                List<Series> addSeriesList = originalBrand.addSeries(newBrand);
                addSeriesList.forEach(addSeries -> seriesMapper.save(originalBrand.getId(), addSeries));
            }
        }));
    }

    /**
     * 수정할 제조사, 브랜드, 시리즈는 newMaker 에서 id(식별자)가 존재한다.
     * 객체의 필드값을 비교해서 originalMaker 와 newMaker 의 값이 다르면 수정 대상이다.
     */
    private void modify(Maker newMaker, Maker originalMaker)
    {
        if (entityChangeDetector.isModified(newMaker, originalMaker))
        {
            originalMaker.update(newMaker);
            makerMapper.update(originalMaker);
        }

        originalMaker.getBrands().forEach(originalBrand ->
            newMaker.getBrands().forEach(newBrand ->
            {
                if (!Objects.isNull(newBrand.getId()) && newBrand.getId().equals(originalBrand.getId()))
                {
                    if (entityChangeDetector.isModified(originalBrand, newBrand))
                    {
                        originalBrand.update(newBrand);
                        brandMapper.update(originalBrand);
                    }

                    originalBrand.getSeriesList().forEach(originalSeries ->
                        newBrand.getSeriesList().forEach(newSeries ->
                        {
                            if (!Objects.isNull(newSeries.getId()) && newSeries.getId().equals(originalSeries.getId()))
                            {
                                if (entityChangeDetector.isModified(originalSeries, newSeries))
                                {
                                    originalSeries.update(newSeries);
                                    seriesMapper.update(originalSeries);
                                }
                            }
                        }));
                }
            }));
    }
}
