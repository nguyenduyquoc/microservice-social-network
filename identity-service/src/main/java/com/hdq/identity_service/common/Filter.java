package com.hdq.identity_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private String field;
    private QueryOperator operator;
    private String value;
    private List<Object> values;
    private String relation;

    public static Filter of(String field, QueryOperator operator, String value) {
        return new Filter(field, operator, value, null, null);
    }

    public static Filter queryLike(String field, String value) {
        return new Filter(field, QueryOperator.LIKE, value, null, null);
    }

    public static Filter query(String field, String value) {
        return new Filter(field, QueryOperator.EQUALS, value, null, null);
    }

    public static Filter queryIn(String field, List<Object> values) {
        return new Filter(field, QueryOperator.IN, null, values, null);
    }

    public static Filter queryHas(String relation, String field, Object value) {
        return new Filter(field, QueryOperator.HAS, value.toString(), null, relation);
    }

    public static Filter queryJoinLike(String relation, String field, String value) {
        return new Filter(field, QueryOperator.JOIN_LIKE, value, null, relation);
    }


}
