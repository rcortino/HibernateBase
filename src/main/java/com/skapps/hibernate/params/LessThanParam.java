package com.skapps.hibernate.params;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Date;

public class LessThanParam<T> implements Param<T> {

    private String key;
    private Object max;

    public LessThanParam(String key, Object max) {
        this.key = key;
        this.max = max;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        if (max instanceof Integer) return criteriaBuilder.lessThan(root.get(key), (Integer)max);
        if (max instanceof Long) return criteriaBuilder.lessThan(root.get(key), (Long)max);
        if (max instanceof Date) return criteriaBuilder.lessThan(root.get(key), (Date)max);
        if (max instanceof Character) return criteriaBuilder.lessThan(root.get(key), (Character) max);
        return null;
    }
}
