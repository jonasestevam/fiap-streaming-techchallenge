package com.jonasestevam.streaming.dto;

import lombok.Data;

@Data
public class FilterDTO {

    private String title;
    private String publishedAt;
    private String category;

    private Integer pageNumber = 0;
    private Integer pageSize = 100;
    private String sortBy = "title";
    private Integer sortDirection = -1;

}
