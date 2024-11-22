package com.youcode.citronix.dto.request.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class FarmRequest {
    @NotBlank(message = "Farm name is required")
    String name;
    
    @NotBlank(message = "Farm location is required")
    String location;
    
    @NotNull(message = "Farm area is required")
    @Positive(message = "Farm area must be positive")
    Double area;
}
