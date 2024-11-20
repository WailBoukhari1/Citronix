package com.youcode.citronix.dto.request.production;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class HarvestRequest {
    @NotNull(message = "Farm ID cannot be null")
    private Long farmId;

    @NotNull(message = "Harvest date cannot be null")
    @PastOrPresent(message = "Harvest date cannot be in the future")
    private LocalDateTime harvestDate;

    private String description;

    @Valid
    private List<HarvestDetailRequest> harvestDetails;
}