package com.skapps.hibernate.params;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Date;

public class GreaterEqualParam<T> implements Param<T> {
    private String key;
    private Object min;

    public GreaterEqualParam(String key, Object min) {
        this.key = key;
        this.min = min;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        if (min instanceof Integer) return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Integer)min);
        if (min instanceof Long) return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Long)min);
        if (min instanceof Date) return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Date)min);
        if (min instanceof Character) return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Character) min);
        return null;
    }
}
