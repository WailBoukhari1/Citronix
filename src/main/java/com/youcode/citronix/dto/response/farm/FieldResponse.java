package com.youcode.citronix.dto.response.farm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldResponse {
    private Long id;
    private String name;
    private Double area;
    private Long farmId;
    private String farmName;
    private Boolean isDeleted;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
