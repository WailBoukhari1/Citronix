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
    @NotNull(message = "Tree ID is required")
    private Long treeId;

    @NotNull(message = "Field ID is required")
    private Long fieldId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Double quantity;
}
