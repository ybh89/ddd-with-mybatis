package com.demo.dddwithmybatis.v3.domain.repository;

import com.demo.dddwithmybatis.v3.domain.model.Maker;

import java.util.Optional;

public interface MakerAggregateRepositoryV3
{
    Maker save(Maker maker);
    Maker update(Maker originalMaker, Maker newMaker);
    Optional<Maker> findById(Long id);
}
