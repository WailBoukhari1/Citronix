package com.youcode.citronix.dto.response.production;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.youcode.citronix.entity.enums.Season;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestResponse {
    private Long id;
    private Long farmId;
    private String farmName;
    private LocalDateTime harvestDate;
    private Season season;
    private String description;
    private Double totalQuantity;
    
    @Builder.Default
    private List<HarvestDetailResponse> harvestDetails = new ArrayList<>();
    private Boolean isDeleted;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
