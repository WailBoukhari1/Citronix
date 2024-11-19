package com.youcode.citronix.dto.request.farm;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldRequest {
    
    @NotBlank(message = "Field name is required")
    @Size(min = 3, max = 50, message = "Field name must be between 3 and 50 characters")
    private String name;
    
    @NotNull(message = "Area is required")
    @Min(value = 1000, message = "Minimum field size is 0.1 hectare (1,000 m²)")
    @Max(value = 10000000, message = "Maximum field size is 1000 hectares (10,000,000 m²)")
    private Double area;
    
    @NotNull(message = "Farm ID is required")
    private Long farmId;
}
