package com.youcode.citronix.validation;

import org.springframework.stereotype.Component;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.production.HarvestDetail;
import com.youcode.citronix.exception.production.HarvestDetailException;

@Component
public class HarvestDetailValidator {

    public void validateHarvestDetailCreation(HarvestDetailRequest request, Harvest harvest, Tree tree) {
        validateCommon(request);
        validateHarvest(harvest);
        validateTree(tree);
        validateQuantity(request.getQuantity());
        validateTreeNotHarvestedInSeason(tree, harvest);
        validateTreeProductivity(tree, request.getQuantity());
    }

    public void validateHarvestDetailUpdate(HarvestDetailRequest request, HarvestDetail existingHarvestDetail, Tree tree) {
        validateCommon(request);
        validateHarvest(existingHarvestDetail.getHarvest());
        validateTree(tree);
        validateQuantity(request.getQuantity());
        validateTreeNotHarvestedInSeason(tree, existingHarvestDetail.getHarvest());
        validateTreeProductivity(tree, request.getQuantity());
    }

    private void validateCommon(HarvestDetailRequest request) {
        if (request == null) {
            throw new HarvestDetailException("HarvestDetail request cannot be null");
        }
        if (request.getTreeId() == null) {
            throw new HarvestDetailException("Tree ID cannot be null");
        }
    }

    private void validateHarvest(Harvest harvest) {
        if (harvest.getIsDeleted()) {
            throw new HarvestDetailException("Cannot create/update harvest detail for a deleted harvest");
        }
    }

    private void validateTree(Tree tree) {
        if (tree == null) {
            throw new HarvestDetailException("Tree cannot be null");
        }
        if (tree.getIsDeleted()) {
            throw new HarvestDetailException("Cannot create/update harvest detail for a deleted tree");
        }
        int age = tree.getAge();
        if (age > 20) {
            throw new HarvestDetailException("Tree is too old (>20 years) and not productive");
        }
    }

    private void validateQuantity(Double quantity) {
        if (quantity == null) {
            throw new HarvestDetailException("Quantity cannot be null");
        }
        if (quantity <= 0) {
            throw new HarvestDetailException("Quantity must be greater than 0");
        }
    }

    private void validateTreeNotHarvestedInSeason(Tree tree, Harvest harvest) {
        boolean alreadyHarvested = harvest.getHarvestDetails().stream()
                .anyMatch(hd -> hd.getTree().equals(tree));
        if (alreadyHarvested) {
            throw new HarvestDetailException("Tree already harvested in this season");
        }
    }

    private void validateTreeProductivity(Tree tree, Double quantity) {
        double maxProductivity = tree.calculateSeasonalProductivity();
        if (quantity > maxProductivity) {
            throw new HarvestDetailException(
                String.format("Quantity cannot exceed maximum seasonal productivity (%,.2f kg)", maxProductivity));
        }
    }

}