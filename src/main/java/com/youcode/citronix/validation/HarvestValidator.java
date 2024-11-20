package com.youcode.citronix.validation;

import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.exception.production.HarvestException;
import com.youcode.citronix.repository.production.HarvestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HarvestValidator {
    
    private final HarvestRepository harvestRepository;

    public void validateHarvestCreation(HarvestRequest request) {
        validateHarvestDate(request.getHarvestDate());
        validateHarvestDetails(request);
    }

    public void validateHarvestUpdate(HarvestRequest request, Harvest existingHarvest) {
        validateHarvestDate(request.getHarvestDate());
        validateHarvestDetails(request);
        validateHarvestStatus(existingHarvest);
    }

    private void validateHarvestDate(LocalDateTime harvestDate) {
        if (harvestDate.isAfter(LocalDateTime.now())) {
            throw new HarvestException("Harvest date cannot be in the future");
        }
    }

    private void validateHarvestDetails(HarvestRequest request) {
        if (request.getHarvestDetails().isEmpty()) {
            throw new HarvestException("At least one harvest detail is required");
        }
    }

    public void validateTreeForHarvest(Tree tree, LocalDateTime harvestDate) {
        if (tree.getIsDeleted()) {
            throw new HarvestException("Cannot harvest from a deleted tree");
        }

        if (!tree.isActive()) {
            throw new HarvestException("Cannot harvest from an inactive tree");
        }

        if (!tree.getField().isActive()) {
            throw new HarvestException("Cannot harvest from a tree in an inactive field");
        }

        validateTreeHarvestFrequency(tree, harvestDate);
    }

    private void validateTreeHarvestFrequency(Tree tree, LocalDateTime newHarvestDate) {
        List<Harvest> recentHarvests = harvestRepository.findByTreeAndDateRange(
            tree.getId(), 
            newHarvestDate.minusDays(30), 
            newHarvestDate
        );

        if (!recentHarvests.isEmpty()) {
            throw new HarvestException("Tree was already harvested within the last 30 days");
        }
    }

    private void validateHarvestStatus(Harvest harvest) {
        if (harvest.getIsDeleted()) {
            throw new HarvestException("Cannot update a deleted harvest");
        }
    }

    private void validateSeasonalHarvest(LocalDateTime harvestDate) {
        int month = harvestDate.getMonthValue();
        Season season;
        
        if (month <= 3) season = Season.WINTER;
        else if (month <= 6) season = Season.SPRING;
        else if (month <= 9) season = Season.SUMMER;
        else season = Season.AUTUMN;
        
        LocalDateTime seasonStart = harvestDate.withMonth(season.getStartMonth()).withDayOfMonth(1);
        LocalDateTime seasonEnd = seasonStart.plusMonths(3).minusDays(1);
        
        if (harvestRepository.existsByHarvestDateBetween(seasonStart, seasonEnd)) {
            throw new HarvestException("A harvest already exists for this season");
        }
    }
} 