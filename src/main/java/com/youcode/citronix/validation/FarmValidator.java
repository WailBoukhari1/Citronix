package com.youcode.citronix.validation;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.exception.farm.FarmException;

@Component
public class FarmValidator {

    public void validateFarmCreation(FarmRequest request) {
        validateBasicFields(request);
    }

    public void validateFarmUpdate(Farm existingFarm, FarmRequest request) {
        validateBasicFields(request);
        validateAreaUpdate(existingFarm, request.getArea());
    }

    private void validateBasicFields(FarmRequest request) {
        validateArea(request.getArea());
        validateCreationDate(request.getCreationDate());
    }

    private void validateArea(Double area) {
        if (area == null) {
            throw new FarmException("Farm area cannot be null");
        }
        if (area < 1000) {
            throw new FarmException("Farm area must be at least 1000 square meters (0.1 hectare)");
        }
        if (area > 10_000_000) {
            throw new FarmException("Farm area cannot exceed 1000 hectares (10,000,000 m²)");
        }
    }

    private void validateAreaUpdate(Farm farm, Double newArea) {
        Double totalFieldArea = farm.getTotalFieldArea();
        if (totalFieldArea > newArea) {
            throw new FarmException(String.format(
                "Cannot reduce farm area below total field area. Current total field area: %.2f m²", 
                totalFieldArea));
        }
    }

    private void validateCreationDate(LocalDate date) {
        if (date != null) {
            if (date.isAfter(LocalDate.now())) {
                throw new FarmException("Creation date cannot be in the future");
            }
        }
    }
}
