package com.demo.dddwithmybatis.v2.domain.repository;

import com.demo.dddwithmybatis.v2.domain.model.Maker;

import java.util.Optional;

public interface MakerAggregateRepository {
    Maker save(Maker maker);
    Maker update(Maker originalMaker, Maker newMaker);
    Optional<Maker> findById(Long id);
}
