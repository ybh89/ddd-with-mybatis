package com.demo.dddwithmybatis.v3;

import com.demo.dddwithmybatis.v3.domain.model.Brand;
import com.demo.dddwithmybatis.v3.domain.model.Maker;
import com.demo.dddwithmybatis.v3.infrastructure.EntityComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntityComparatorTest
{
    @DisplayName("필드값이 다른 경우")
    @Test
    void EntityComparatorTest() throws IllegalAccessException
    {
        // given
        Maker originalMaker = Maker.create("originalMaker", null);
        Maker newMaker = Maker.create("newMaker", null);

        // when
        boolean modified = EntityComparator.isModified(originalMaker, newMaker);

        // then
        assertThat(modified).isTrue();
    }

    @DisplayName("필드값이 모두 같은 경우")
    @Test
    void 필드값_모두_같은경우() throws IllegalAccessException
    {
        // given
        Maker originalMaker = Maker.create("originalMaker", null);
        Maker newMaker = Maker.create("originalMaker", null);

        // when
        boolean modified = EntityComparator.isModified(originalMaker, newMaker);

        // then
        assertThat(modified).isFalse();
    }

    @DisplayName("타입이 다른 경우 - 실패")
    @Test
    void 타입이_다른경우_실패() throws IllegalAccessException
    {
        // given
        Maker originalMaker = Maker.create("originalMaker", null);
        Brand brand = Brand.create("brand", null);

        // when
        // then
        assertThatThrownBy(() -> EntityComparator.isModified(originalMaker, brand))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
