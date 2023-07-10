package com.skapps.hibernate.params;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface Param<T> {

    Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> root);

}
