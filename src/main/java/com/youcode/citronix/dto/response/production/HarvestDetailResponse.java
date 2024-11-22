package com.youcode.citronix.dto.response.production;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailResponse {
    private Long id;
    private Long harvestId;
    private Long treeId;
    private String treeName;
    private Double quantity;
    private Double treeProductivity;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
