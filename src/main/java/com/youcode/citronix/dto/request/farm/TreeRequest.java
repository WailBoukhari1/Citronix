package com.youcode.citronix.dto.request.farm;

import java.time.LocalDate;

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
    
    @NotBlank(message = "Tree name cannot be blank")
    @Size(min = 3, max = 50, message = "Tree name must be between 3 and 50 characters")
    private String name;
    
    @NotNull(message = "Field ID cannot be null")
    private Long fieldId;

    @NotNull(message = "Planting date cannot be null")
    @PastOrPresent(message = "Planting date cannot be in the future")
    private LocalDate plantationDate;

    private String description;
}
