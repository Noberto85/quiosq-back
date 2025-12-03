package com.webone.quiosq.dto;

import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Data
public class PageableDto<T> {

    private List<T> content;
    private Pageable pageable;
    private int totalPages;
    private long totalElements;

    public PageableDto(Page<T> page) {
        this.content = page.getContent();
        this.pageable = page.getPageable();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
