package com.hdq.identity_service.core;

import com.hdq.identity_service.common.Filter;
import com.hdq.identity_service.utils.StringUtil;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BaseSpecifications<T> {

    public static <T> Specification<T> getSpecification(List<Filter> filters) {
        BaseSpecifications<T> baseSpecifications = new BaseSpecifications<>();
        return baseSpecifications.getQueryResult(filters);
    }

    public static <T> Specification<T> ofEmpty() {
        BaseSpecifications<T> baseSpecifications = new BaseSpecifications<>();
        return baseSpecifications.getQueryResult(null);
    }


    public Specification<T> getQueryResult(List<Filter> filters) {
        return (filters != null && !filters.isEmpty())
                ? getSpecificationFromFilters(filters)
                : Specification.where(null);
    }

    protected Specification<T> getSpecificationFromFilters(List<Filter> filter) {
        Specification<T> specification = Specification.where(null);
        for (Filter input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    protected Specification<T> createSpecification(Filter input) {
        return switch (input.getOperator()) {
            case EQUALS -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(input.getField()),
                            castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case NOT_EQ -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.notEqual(root.get(input.getField()),
                            castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.gt(root.get(input.getField()),
                            (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LESS_THAN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lt(root.get(input.getField()),
                            (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LIKE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(input.getField()), "%" + input.getValue() + "%");
            case IN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.in(root.get(input.getField()))
                            .value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues()));
            case HAS -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.join(input.getRelation(), JoinType.INNER).get(input.getField()), input.getValue());
            case JOIN_LIKE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.join(input.getRelation(), JoinType.INNER).get(input.getField()), "%" + input.getValue() + "%");
        };
    }


    public Specification<T> exactIgnoreCase(String property, String term) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get(property)), term);
    }

    public Specification<T> containsIgnoreCase(String property, String searchTerm) {
        return (root, query, cb) -> {
            String containsLikePattern = getContainsLikePattern(searchTerm);
            return cb.like(cb.lower(root.get(property)), containsLikePattern);
        };
    }

    protected static String getContainsLikePattern(String searchTerm) {
        return StringUtil.isNotEmpty(searchTerm) ? "%" : "%" + searchTerm.toLowerCase() + "%";
    }

    private Object castToRequiredType(Class<?> fieldType, String value) {
        return switch (fieldType.getSimpleName()) {
            case "Double" -> Double.valueOf(value);
            case "Integer" -> Integer.valueOf(value);
            case "Long" -> Long.valueOf(value);
            case "BigDecimal" -> new BigDecimal(value);
            case "Enum" -> Enum.valueOf(fieldType.asSubclass(Enum.class), value);
            default -> value;
        };
    }

    protected Object castToRequiredType(Class<?> fieldType, List<Object> value) {
        List<Object> lists = new ArrayList<>();
        for (Object s : value) {
            lists.add(castToRequiredType(fieldType, s.toString()));
        }
        return lists;
    }
}
