package com.youcode.citronix.dto.response.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {
    private Long id;
    private LocalDate saleDate;
    private Double quantity;
    private Double pricePerUnit;
    private Double revenue;
    private String clientName;
    private Long farmId;
    private String farmName;
    private Long harvestId;
    private LocalDateTime harvestDate;
    private Boolean isDeleted;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
