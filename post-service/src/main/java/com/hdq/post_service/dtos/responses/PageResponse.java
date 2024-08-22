package com.hdq.post_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class PageResponse<T> {

    int totalPages;
    int currentPage;
    int pageSize;
    long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
