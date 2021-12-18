package com.demo.dddwithmybatis.v3.infrastructure;

import com.demo.dddwithmybatis.v3.domain.model.Entity;

import java.lang.reflect.Field;
import java.util.Objects;

public class EntityComparator
{
    public static boolean isModified(final Entity originalEntity, final Entity newEntity)
    {
        Class<? extends Entity> originalEntityClass = originalEntity.getClass();
        Class<? extends Entity> newEntityClass = newEntity.getClass();

        if (!originalEntityClass.getTypeName().equals(newEntityClass.getTypeName()))
        {
            throw new IllegalArgumentException("originalEntity 와 newEntity 의 타입이 다릅니다. originalEntity 타입: "
                + originalEntityClass.getTypeName() + ", newEntity 타입: " + newEntityClass.getTypeName());
        }

        for (Field originalField : originalEntityClass.getDeclaredFields())
        {
            try
            {
                originalField.setAccessible(true); // private 필드 접근 허용
                Object originalFieldValue = originalField.get(originalEntity);
                Field newField = newEntityClass.getDeclaredField(originalField.getName());
                newField.setAccessible(true);
                Object newFieldValue = newField.get(newEntity);

                System.out.println("field name: " + originalField.getName() + " originalFieldValue: "
                    + originalFieldValue + " newFieldValue: " + newFieldValue);

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
}
