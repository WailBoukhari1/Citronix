package com.youcode.citronix.dto.request.production;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Harvest date is required")
    private LocalDateTime harvestDate;

    @NotNull(message = "Harvest details are required")
    @Size(min = 1, message = "At least one harvest detail is required")
    @Valid
    private List<HarvestDetailRequest> harvestDetails;
}
