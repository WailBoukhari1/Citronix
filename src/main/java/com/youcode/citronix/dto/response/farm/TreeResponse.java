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
public class TreeResponse {
    private Long id;
    private String name;
    private Long fieldId;
    private String fieldName;
    private Boolean isDeleted;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
