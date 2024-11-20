package com.youcode.citronix.dto.request.production;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailRequest {
    @NotNull(message = "Harvest ID cannot be null")
    private Long harvestId;

    @NotNull(message = "Tree ID cannot be null")
    private Long treeId;

    @NotNull(message = "Field ID cannot be null")
    private Long fieldId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be greater than 0")
    private Double quantity;
}