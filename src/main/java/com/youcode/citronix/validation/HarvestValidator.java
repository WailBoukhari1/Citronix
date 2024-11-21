package com.youcode.citronix.validation;

import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.exception.production.HarvestException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HarvestValidator {

    public void validateHarvestCreation(HarvestRequest request, Farm farm) {
        validateCommon(request);
        validateFarm(farm);
        validateHarvestDate(request.getHarvestDate());
    }

    public void validateHarvestUpdate(HarvestRequest request, Harvest existingHarvest, Farm farm) {
        validateCommon(request);
        validateFarm(farm);
        validateHarvestDate(request.getHarvestDate());
        validateExistingHarvest(existingHarvest);
    }

    private void validateCommon(HarvestRequest request) {
        if (request == null) {
            throw new HarvestException("Harvest request cannot be null");
        }
        if (request.getFarmId() == null) {
            throw new HarvestException("Farm ID cannot be null");
        }
        if (request.getSeason() == null) {
            throw new HarvestException("Season cannot be null");
        }
    }

    private void validateFarm(Farm farm) {
        if (farm.getIsDeleted()) {
            throw new HarvestException("Cannot create/update harvest for a deleted farm");
        }
    }

    private void validateHarvestDate(LocalDateTime harvestDate) {
        if (harvestDate == null) {
            throw new HarvestException("Harvest date cannot be null");
        }
        if (harvestDate.isAfter(LocalDateTime.now())) {
            throw new HarvestException("Harvest date cannot be in the future");
        }
    }

    private void validateExistingHarvest(Harvest harvest) {
        if (harvest.getIsDeleted()) {
            throw new HarvestException("Cannot update a deleted harvest");
        }
    }
}
