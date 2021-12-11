package com.demo.dddwithmybatis.v1.application;

import com.demo.dddwithmybatis.v1.domain.model.Maker;
import com.demo.dddwithmybatis.v1.domain.repository.BrandRepository;
import com.demo.dddwithmybatis.v1.domain.repository.MakerRepository;
import com.demo.dddwithmybatis.v1.domain.repository.SeriesRepository;
import com.demo.dddwithmybatis.v1.dto.MakerRequest;
import com.demo.dddwithmybatis.v1.dto.MakerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MakerService {
    private final MakerRepository makerRepository;
    private final BrandRepository brandRepository;
    private final SeriesRepository seriesRepository;

    @Transactional
    public Long create(MakerRequest makerRequest) {
        Maker maker = MakerAggregateFactory.from(makerRequest);
        makerRepository.save(maker);
        maker.getBrands().forEach(brand -> {
            brandRepository.save(brand);
            brand.getSeriesList().forEach(seriesRepository::save);
        });
        return maker.getId();
    }

    @Transactional
    public Long update(MakerRequest makerRequest) {

        return null;
    }

    @Transactional(readOnly = true)
    public MakerResponse retrieve(Long makerId) {
        Maker maker = findById(makerId);
        return MakerResponse.from(maker);
    }

    private Maker findById(Long makerId) {
        return makerRepository.findById(makerId)
                .orElseThrow(() -> new IllegalStateException("제조사를 찾을 수 없습니다."));
    }
}
