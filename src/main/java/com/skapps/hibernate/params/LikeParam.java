package com.skapps.hibernate.params;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LikeParam<T> implements Param<T>{

    private String key;
    private String value;

    public LikeParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.like(root.get(key), value);
    }
}
