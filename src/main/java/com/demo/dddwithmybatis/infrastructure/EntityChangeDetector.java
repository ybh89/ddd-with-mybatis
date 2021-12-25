package com.demo.dddwithmybatis.infrastructure;

import com.demo.dddwithmybatis.domain.Entity;
import com.demo.dddwithmybatis.domain.IgnoreEntityChangeDetector;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Objects;

@Component
public class EntityChangeDetector
{
    public boolean isModified(final Object originalEntity, final Object newEntity)
    {
        Class<? extends Object> originalEntityClass = originalEntity.getClass();
        Class<? extends Object> newEntityClass = newEntity.getClass();

        validate(originalEntityClass, newEntityClass);

        for (Field originalField : originalEntityClass.getDeclaredFields())
        {
            // 클래스, 필드에 IgnoreEntityChangeDetector 어노테이션이 선언된 객체이면 비교 대상에서 제외
            if (hasIgnoreEntityChangeDetectorAnnotation(originalField)) continue;

            // 엔티티 필드는 비교 대상에서 제외
            if (isEntity(originalField)) continue;

            try
            {
                originalField.setAccessible(true); // private 필드 접근 허용
                Object originalFieldValue = originalField.get(originalEntity);
                Field newField = newEntityClass.getDeclaredField(originalField.getName());
                newField.setAccessible(true);
                Object newFieldValue = newField.get(newEntity);

                if (Objects.isNull(originalFieldValue) && Objects.isNull(newFieldValue))
                {
                    continue;
                }
                if (Objects.isNull(originalFieldValue) || !originalFieldValue.equals(newFieldValue))
                {
                    return true;
                }
            }
            catch (IllegalAccessException e)
            {
                // 사실상 발생 불가
                throw new IllegalStateException("값에 접근할 수 없습니다.", e);
            }
            catch (NoSuchFieldException e)
            {
                // 사실상 발생 불가
                throw new IllegalArgumentException("필드를 찾을 수 없습니다", e);
            }
        }
        return false;
    }

    private void validate(Class<?> originalEntityClass, Class<?> newEntityClass)
    {
        if (Objects.isNull(originalEntityClass.getDeclaredAnnotation(Entity.class)))
        {
            throw new IllegalArgumentException("엔티티가 아닙니다. originalEntity type: " + originalEntityClass.getTypeName());
        }
        if (Objects.isNull(newEntityClass.getDeclaredAnnotation(Entity.class)))
        {
            throw new IllegalArgumentException("엔티티가 아닙니다. newEntity type: " + newEntityClass.getTypeName());
        }
        if (!originalEntityClass.getTypeName().equals(newEntityClass.getTypeName()))
        {
            throw new IllegalArgumentException("originalEntity 와 newEntity 의 타입이 다릅니다. originalEntity 타입: "
                + originalEntityClass.getTypeName() + ", newEntity 타입: " + newEntityClass.getTypeName());
        }
    }

    private boolean isEntity(Field field)
    {
        if (Collection.class.isAssignableFrom(field.getType())) {
            Class<?> genericClass;
            try
            {
                genericClass = Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName());
            }
            catch (ClassNotFoundException e)
            {
                throw new IllegalStateException("클래스를 찾을 수 없습니다. (ClassNotFoundException)", e);
            }

            if (Objects.nonNull(genericClass.getDeclaredAnnotation(Entity.class)))
            {
                return true;
            }
        }
        if (Objects.nonNull(field.getType().getDeclaredAnnotation(Entity.class)))
        {
            return true;
        }
        return false;
    }

    private boolean hasIgnoreEntityChangeDetectorAnnotation(Field originalField)
    {
        if (Objects.nonNull(originalField.getType().getDeclaredAnnotation(IgnoreEntityChangeDetector.class))
            || Objects.nonNull(originalField.getDeclaredAnnotation(IgnoreEntityChangeDetector.class)))
        {
            return true;
        }
        return false;
    }
}
