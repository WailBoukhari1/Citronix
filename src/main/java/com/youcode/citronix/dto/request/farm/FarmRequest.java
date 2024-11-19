package com.youcode.citronix.dto.request.farm;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmRequest {
    
    @NotBlank(message = "Farm name is required")
    @Size(min = 3, max = 100, message = "Farm name must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Farm name can only contain letters, numbers, spaces, and hyphens")
    private String name;
    
    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 200, message = "Location must be between 2 and 200 characters")
    private String location;
    
    @NotNull(message = "Area is required")
    @Min(value = 1000, message = "Farm area must be at least 1000 square meters")
    @Max(value = 10_000_000, message = "Farm area cannot exceed 1000 hectares (10,000,000 m²)")
    private Double area;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDate creationDate;
}
