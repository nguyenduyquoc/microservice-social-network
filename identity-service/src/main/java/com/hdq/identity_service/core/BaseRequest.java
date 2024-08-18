package com.hdq.identity_service.core;

import com.hdq.identity_service.common.Filter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BaseRequest extends BasePaginationRequest implements Serializable {


    List<Filter> filters;

    public boolean isPaginationRequest() {
        return this.getLimit() != null;
    }

    public void addFilter(Filter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
    }

}