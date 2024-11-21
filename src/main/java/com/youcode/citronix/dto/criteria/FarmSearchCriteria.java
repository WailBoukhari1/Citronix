package com.youcode.citronix.dto.criteria;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FarmSearchCriteria {
    private String name;
    private String location;
    private Double minArea;
    private Double maxArea;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isDeleted;
} 