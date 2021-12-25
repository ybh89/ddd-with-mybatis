package com.demo.dddwithmybatis.domain.repository;

import com.demo.dddwithmybatis.domain.model.maker.Maker;

import java.util.Optional;

public interface MakerAggregateRepository
{
    Maker save(Maker maker);
    Maker update(Maker originalMaker, Maker newMaker);
    Optional<Maker> findById(Long id);
}
