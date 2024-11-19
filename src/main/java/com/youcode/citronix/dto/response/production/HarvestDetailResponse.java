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
    private Long fieldId;
    private String fieldName;
    private Double quantity;
    private Boolean isDeleted;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
