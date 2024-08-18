package com.hdq.identity_service.core;

import com.hdq.identity_service.common.Filter;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePaginationRequest {

    @Digits(integer = 3, fraction = 0, message = "limit không hợp lệ")
    @Positive(message = "limit phải là số lớn hơn 0")
    private BigDecimal limit;

    @Digits(integer = 3, fraction = 0, message = "page không hợp lệ")
    @Positive(message = "page phải là số lớn hơn 0")
    private BigDecimal page;

    private String orderBy;


    public Sort getSort() {
        if (orderBy == null || orderBy.isEmpty()) {
            return null;
        }
        List<Sort.Order> orders = new ArrayList<>();
        String[] splits = orderBy.split(",");
        for (String s : splits) {
            String[] f = s.split(":");
            if (f.length == 1) {
                orders.add(new Sort.Order(Sort.Direction.ASC, f[0]));
            } else {
                if ("DESC".equalsIgnoreCase(f[1])) {
                    orders.add(new Sort.Order(Sort.Direction.DESC, f[0]));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.ASC, f[0]));
                }
            }
        }
        return Sort.by(orders);
    }

    public List<Filter> getFilters() {
        return new ArrayList<>();
    }

    public Integer getLimit() {
        if (this.limit != null) {
            return this.limit.intValue();
        }
        return null;
    }

    public Integer getPage() {
        if (this.page != null) {
            return this.page.intValue() - 1;
        }
        return null;
    }


    public Pageable getPageable() {
        Sort sort = getSort();
        if (sort != null) {
            return PageRequest.of(page.intValue(), limit.intValue(), sort);
        }
        return PageRequest.of(page.intValue(), limit.intValue());
    }
}
