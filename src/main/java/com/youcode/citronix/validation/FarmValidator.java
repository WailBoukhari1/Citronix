package com.youcode.citronix.validation;

import org.springframework.stereotype.Component;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.exception.farm.FarmException;

@Component
public class FarmValidator {
    
    public void validateFarmCreation(FarmRequest request) {
        validateArea(request.getArea());
        validateName(request.getName());
    }
    
    public void validateFarmUpdate(Farm existingFarm, FarmRequest request) {
        validateArea(request.getArea());
        validateName(request.getName());
        if (request.getArea() < existingFarm.getTotalFieldArea()) {
            throw new FarmException("New area cannot be less than total field area");
        }
    }

    private void validateArea(Double area) {
        if (area == null) {
            throw new FarmException("Farm area is required");
        }
        if (area <= 0) {
            throw new FarmException("Farm area must be positive");
        }
        if (area > 100_000_000) { // 10,000 hectares
            throw new FarmException("Farm area cannot exceed 10,000 hectares");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new FarmException("Farm name is required");
        }
        if (name.length() < 3 || name.length() > 50) {
            throw new FarmException("Farm name must be between 3 and 50 characters");
        }
    }
}
