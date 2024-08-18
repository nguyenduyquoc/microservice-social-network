package com.hdq.identity_service.core;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasePagination<BaseDTO> {
    private Integer currentPage;
    private Integer sizePage;
    private Integer lastPage;
    private Long total;
    private List<BaseDTO> data;

    public BasePagination(Page<BaseDTO> page) {
        this.currentPage = page.getPageable().getPageNumber() + 1;
        this.sizePage = page.getPageable().getPageSize();
        this.lastPage = page.getTotalPages();
        this.total = page.getTotalElements();
        this.data = page.getContent();
    }

}
