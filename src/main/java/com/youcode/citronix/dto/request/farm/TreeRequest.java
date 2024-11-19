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
public class TreeRequest {
    
    @NotBlank(message = "Tree name is required")
    @Size(min = 3, max = 50, message = "Tree name must be between 3 and 50 characters")
    private String name;
    
    @NotNull(message = "Field ID is required")
    private Long fieldId;
}
