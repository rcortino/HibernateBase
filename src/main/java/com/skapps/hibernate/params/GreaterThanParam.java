package com.skapps.hibernate.params;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Date;

public class GreaterThanParam<T> implements Param<T> {

    private String key;
    private Object min;

    public GreaterThanParam(String key, Object min) {
        this.key = key;
        this.min = min;
    }
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        if (min instanceof Integer) return criteriaBuilder.greaterThan(root.get(key), (Integer)min);
        if (min instanceof Long) return criteriaBuilder.greaterThan(root.get(key), (Long)min);
        if (min instanceof Date) return criteriaBuilder.greaterThan(root.get(key), (Date)min);
        if (min instanceof Character) return criteriaBuilder.greaterThan(root.get(key), (Character) min);
        return null;
    }
}
