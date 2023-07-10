package com.skapps.hibernate.params;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Date;


public class BetweenParam<T> implements Param<T>{
    private String key;
    private Object min;
    private Object max;

    public BetweenParam(String key, Object min, Object max) {
        this.key = key;
        this.min = min;
        this.max = max;
    }
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        if (min instanceof Integer) return criteriaBuilder.between(root.get(key), (Integer)min, (Integer)max);
        if (min instanceof Long) return criteriaBuilder.between(root.get(key), (Long)min, (Long)max);
        if (min instanceof Date) return criteriaBuilder.between(root.get(key), (Date)min, (Date)max);
        if (min instanceof Character) return criteriaBuilder.between(root.get(key), (Character) min, (Character) max);
        return null;
    }
}
