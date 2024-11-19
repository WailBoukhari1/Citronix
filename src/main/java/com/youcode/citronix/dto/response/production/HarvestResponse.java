package com.youcode.citronix.dto.response.production;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestResponse {
    private Long id;
    private LocalDateTime harvestDate;
    private List<HarvestDetailResponse> harvestDetails;
    private Double totalQuantity;
    private Boolean isDeleted;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
