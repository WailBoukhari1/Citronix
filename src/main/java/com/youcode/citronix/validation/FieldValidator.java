package com.youcode.citronix.validation;

import org.springframework.stereotype.Component;

import com.youcode.citronix.dto.request.farm.FieldRequest;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.exception.farm.FieldException;

@Component
public class FieldValidator {

    public void validateFieldCreation(FieldRequest request, Farm farm) {
        validateBasicFields(request);
        validateFieldInFarm(request.getArea(), farm);
    }

    public void validateFieldUpdate(FieldRequest request, Farm farm, Field existingField) {
        validateBasicFields(request);
        if (!request.getArea().equals(existingField.getArea())) {
            Double areaChange = request.getArea() - existingField.getArea();
            validateFieldAreaChange(areaChange, farm);
        }
    }

    private void validateBasicFields(FieldRequest request) {
        validateArea(request.getArea());
        validateName(request.getName());
    }

    private void validateArea(Double area) {
        if (area == null) {
            throw new FieldException("Area is required");
        }
        if (area < 1000) {
            throw new FieldException("Minimum field size is 0.1 hectare (1,000 m²)");
        }
        if (area > 10_000_000) {
            throw new FieldException("Maximum field size is 1000 hectares (10,000,000 m²)");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new FieldException("Field name is required");
        }
        if (name.length() < 3 || name.length() > 50) {
            throw new FieldException("Field name must be between 3 and 50 characters");
        }
    }

    private void validateFieldInFarm(Double fieldArea, Farm farm) {
        if (farm.getTotalFieldArea() + fieldArea > farm.getArea()) {
            throw new FieldException("Total field area would exceed farm area");
        }
    }

    private void validateFieldAreaChange(Double areaChange, Farm farm) {
        if (areaChange > 0 && (farm.getTotalFieldArea() + areaChange > farm.getArea())) {
            throw new FieldException("Total field area would exceed farm area");
        }
    }
}
