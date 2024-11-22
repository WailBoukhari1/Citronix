package com.youcode.citronix.dto.request.sales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {
    @NotNull(message = "Sale date is required")
    private LocalDate saleDate;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Double quantity;

    @NotNull(message = "Price per unit is required")
    @Positive(message = "Price per unit must be positive")
    private Double pricePerUnit;

    @NotNull(message = "Client name is required")
    @NotBlank(message = "Client name cannot be empty")
    private String clientName;

    @NotNull(message = "Farm ID is required")
    private Long farmId;

    @NotNull(message = "Harvest ID is required")
    private Long harvestId;
}
