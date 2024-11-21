package com.youcode.citronix.dto.request.production;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class HarvestRequest {
    @NotNull(message = "Farm ID cannot be null")
    private Long farmId;

    @NotNull(message = "Harvest date cannot be null")
    @PastOrPresent(message = "Harvest date cannot be in the future")
    private LocalDateTime harvestDate;

    @NotNull(message = "Season cannot be null")
    private Season season;

    private String description;

    @Valid
    @Builder.Default
    private List<HarvestDetailRequest> harvestDetails = new ArrayList<>();
    
    @Builder.Default
    private Boolean isDeleted = false;
}