package com.demo.dddwithmybatis.v2.application;

import com.demo.dddwithmybatis.v2.domain.model.Maker;
import com.demo.dddwithmybatis.v2.domain.repository.MakerAggregateRepository;
import com.demo.dddwithmybatis.v2.dto.maker.MakerResponse;
import com.demo.dddwithmybatis.v2.dto.maker.MakerSaveRequest;
import com.demo.dddwithmybatis.v2.dto.maker.MakerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MakerServiceV2 {
    private final MakerAggregateRepository makerAggregateRepository;

    @Transactional
    public MakerResponse create(MakerSaveRequest makerSaveRequest) {
        Maker maker = MakerAggregateFactory.from(makerSaveRequest);
        Maker savedMaker = makerAggregateRepository.save(maker);
        return MakerResponse.from(savedMaker);
    }

    @Transactional
    public MakerResponse update(MakerUpdateRequest makerUpdateRequest) {
        Maker newMaker = MakerAggregateFactory.from(makerUpdateRequest);
        Maker originalMaker = findMakerById(makerUpdateRequest.getId());
        Maker maker = makerAggregateRepository.update(originalMaker, newMaker);
        return MakerResponse.from(maker);
    }

    @Transactional(readOnly = true)
    public MakerResponse retrieve(Long makerId) {
        Maker maker = findMakerById(makerId);
        return MakerResponse.from(maker);
    }

    private Maker findMakerById(Long makerId) {
        return makerAggregateRepository.findById(makerId)
                .orElseThrow(() -> new IllegalStateException("제조사를 찾을 수 없습니다."));
    }
}
