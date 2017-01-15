package com.wolf.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.toArray;

/**
 * Created by wolf on 17/1/12.
 */
public class LocalSpecification {

    public static <T> Specification<T> byAuto(final EntityManager entityManager, final T entity) {

        //获取当前实体对象类的类型
        final Class<T> type = (Class<T>) entity.getClass();

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // Predicate 列表存储构造的查询条件
                List<Predicate> predicates = new ArrayList<>();
                //获取实体类的EntityType，可以从EntityType获得实体类的属性
                EntityType<T> entityType = entityManager.getMetamodel().entity(type);

                //遍历实体类的所有属性
                for (Attribute<T, ?> attr : entityType.getDeclaredAttributes()) {
                    Object attrValue = getValue(entity, attr);
                    if (attrValue == null) {
                        continue;
                    }

                    if (attr.getJavaType() == String.class) {  //String用like模糊查询
                        if (!StringUtils.isEmpty(attrValue)) {
                            predicates.add(criteriaBuilder.like(root.get(attribute(entityType, attr.getName(),
                                    String.class)), pattern((String) attrValue)));
                        }
                    }else {  // 非String类型用equal
                        predicates.add(criteriaBuilder.equal(root.get(attribute(entityType, attr.getName(),
                                attrValue.getClass())), attrValue));
                    }
                }

                // 讲条件转换成Predicate
                return predicates.isEmpty() ? criteriaBuilder.conjunction()
                        : criteriaBuilder.and(toArray(predicates, Predicate.class));
            }

            private <T> Object getValue(T entity, Attribute<T, ?> attr) {
                return ReflectionUtils.getField((Field) attr.getJavaMember(), entity);
            }

            private <E, T> SingularAttribute<T, E> attribute(EntityType<T> entityType, String fieldName, Class<E> fieldClass) {
                return entityType.getDeclaredSingularAttribute(fieldName, fieldClass);
            }

        };
    }

    private static String pattern(String param) {
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(param);
        sb.append("%");
        return sb.toString();
    }
}
