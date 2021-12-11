package com.demo.dddwithmybatis.v1.application;

import com.demo.dddwithmybatis.v1.domain.model.Brand;
import com.demo.dddwithmybatis.v1.domain.model.Maker;
import com.demo.dddwithmybatis.v1.domain.repository.BrandRepository;
import com.demo.dddwithmybatis.v1.domain.repository.MakerRepository;
import com.demo.dddwithmybatis.v1.domain.repository.SeriesRepository;
import com.demo.dddwithmybatis.v1.dto.MakerRequest;
import com.demo.dddwithmybatis.v1.dto.MakerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MakerService {
    private final MakerRepository makerRepository;
    private final BrandRepository brandRepository;
    private final SeriesRepository seriesRepository;

    public Long create(MakerRequest makerRequest) {
        Maker maker = MakerAggregateFactory.from(makerRequest);
        makerRepository.save(maker);
        maker.getBrands().forEach(brand -> {
            brandRepository.save(brand);
            brand.getSeriesList().forEach(seriesRepository::save);
        });
        return maker.getId();
    }
}
