package com.wolf.support.impl;

import com.wolf.support.LocalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static com.wolf.specification.LocalSpecification.byAuto;

/**
 * Created by wolf on 17/1/12.
 */
public class LocalRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements
        LocalRepository<T, ID> {

    private final EntityManager entityManager;

    public LocalRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public Page<T> findByAuto(T entity, Pageable pageable) {
        return findAll(byAuto(entityManager, entity), pageable);
    }

}
