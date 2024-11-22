package com.youcode.citronix.dto.criteria;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FarmSearchCriteria {
    private String name;
    private String location;
    private Double minArea;
    private Double maxArea;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
} 