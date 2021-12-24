package com.demo.dddwithmybatis.v3;

import com.demo.dddwithmybatis.v3.domain.Entity;
import com.demo.dddwithmybatis.v3.domain.model.Brand;
import com.demo.dddwithmybatis.v3.domain.model.Maker;
import com.demo.dddwithmybatis.v3.infrastructure.EntityChangeDetector;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntityChangeDetectorTest
{
    private EntityChangeDetector entityChangeDetector = new EntityChangeDetector();

    @DisplayName("필드값이 다른 경우")
    @Test
    void EntityComparatorTest() throws IllegalAccessException
    {
        // given
        Maker originalMaker = Maker.create("originalMaker", null);
        Maker newMaker = Maker.create("newMaker", null);

        // when
        boolean modified = entityChangeDetector.isModified(originalMaker, newMaker);

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
        boolean modified = entityChangeDetector.isModified(originalMaker, newMaker);

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
        assertThatThrownBy(() -> entityChangeDetector.isModified(originalMaker, brand))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("연관 엔티티는 비교 대상에서 제외")
    @Test
    void 엔티티_비교대상에서_제외()
    {
        // given
        TestRegister testRegister1 = new TestRegister("creatorId", null, null, null);
        ChildEntity childEntity = new ChildEntity(1L, "아들");
        TestEntity originalTestEntity = new TestEntity(1L, "테스트", "ignore1", null, null, testRegister1);
        TestEntity newTestEntity = new TestEntity(1L, "테스트", "ignore1", null, childEntity, testRegister1);

        // when
        boolean modified = entityChangeDetector.isModified(originalTestEntity, newTestEntity);

        // then
        assertThat(modified).isFalse();
    }

    @DisplayName("연관 엔티티 컬렉션은 비교 대상에서 제외")
    @Test
    void 엔티티컬렉션_비교대상에서_제외()
    {
        // given
        TestRegister testRegister1 = new TestRegister("creatorId", null, null, null);
        ChildEntity childEntity = new ChildEntity(1L, "아들");
        TestEntity originalTestEntity = new TestEntity(1L, "테스트", "ignore1", null, null, testRegister1);
        TestEntity newTestEntity = new TestEntity(1L, "테스트", "ignore1", Arrays.asList(childEntity), null, testRegister1);

        // when
        boolean modified = entityChangeDetector.isModified(originalTestEntity, newTestEntity);

        // then
        assertThat(modified).isFalse();
    }

    @DisplayName("Entity 어노테이션이 없는 객체를 입력하면 실패")
    @Test
    void Entity가_아닌객체_실패()
    {
        // given
        // when
        // then
        assertThatThrownBy(() -> entityChangeDetector.isModified(new Object(), new Object()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageStartingWith("엔티티가 아닙니다.");
    }

    @Getter
    @Entity
    static class TestEntity
    {
        private Long id;
        private String name;
        private String ignore;
        private List<ChildEntity> childEntities;
        private ChildEntity childEntity;
        private TestRegister testRegister;

        public TestEntity(Long id, String name, String ignore, List<ChildEntity> childEntities, ChildEntity childEntity, TestRegister testRegister)
        {
            this.id = id;
            this.name = name;
            this.ignore = ignore;
            this.childEntities = childEntities;
            this.childEntity = childEntity;
            this.testRegister = testRegister;
        }
    }

    @Getter
    @Entity
    static class ChildEntity
    {
        private Long id;
        private String name;

        public ChildEntity(Long id, String name)
        {
            this.id = id;
            this.name = name;
        }
    }

    static class TestRegister
    {
        private final String creatorId;
        private final LocalDateTime createdAt;
        private final String updaterId;
        private final LocalDateTime updatedAt;

        TestRegister(String creatorId, LocalDateTime createdAt, String updaterId, LocalDateTime updatedAt)
        {
            this.creatorId = creatorId;
            this.createdAt = createdAt;
            this.updaterId = updaterId;
            this.updatedAt = updatedAt;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestRegister that = (TestRegister) o;
            return Objects.equals(creatorId, that.creatorId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updaterId, that.updaterId) && Objects.equals(updatedAt, that.updatedAt);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(creatorId, createdAt, updaterId, updatedAt);
        }
    }
}
