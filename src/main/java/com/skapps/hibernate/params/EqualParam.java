package com.skapps.hibernate.params;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EqualParam<T> implements Param<T>{

    private String key;
    private Object value;

    public EqualParam(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> root ) {
        return criteriaBuilder.equal(root.get(key), value);
    }
}
