package com.youcode.citronix.validation;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.production.HarvestDetail;
import com.youcode.citronix.exception.production.HarvestDetailException;
import org.springframework.stereotype.Component;

@Component
public class HarvestDetailValidator {

    public void validateHarvestDetailCreation(HarvestDetailRequest request, Harvest harvest, Tree tree, Field field) {
        validateCommon(request);
        validateHarvest(harvest);
        validateTree(tree);
        validateField(field);
        validateTreeField(tree, field);
        validateQuantity(request.getQuantity());
    }

    public void validateHarvestDetailUpdate(HarvestDetailRequest request, HarvestDetail existingHarvestDetail, 
                                          Harvest harvest, Tree tree, Field field) {
        validateCommon(request);
        validateHarvest(harvest);
        validateTree(tree);
        validateField(field);
        validateTreeField(tree, field);
        validateQuantity(request.getQuantity());
        validateExistingHarvestDetail(existingHarvestDetail);
    }

    private void validateCommon(HarvestDetailRequest request) {
        if (request == null) {
            throw new HarvestDetailException("HarvestDetail request cannot be null");
        }
        if (request.getHarvestId() == null) {
            throw new HarvestDetailException("Harvest ID cannot be null");
        }
        if (request.getTreeId() == null) {
            throw new HarvestDetailException("Tree ID cannot be null");
        }
        if (request.getFieldId() == null) {
            throw new HarvestDetailException("Field ID cannot be null");
        }
    }

    private void validateHarvest(Harvest harvest) {
        if (harvest.getIsDeleted()) {
            throw new HarvestDetailException("Cannot create/update harvest detail for a deleted harvest");
        }
    }

    private void validateTree(Tree tree) {
        if (tree.getIsDeleted()) {
            throw new HarvestDetailException("Cannot create/update harvest detail for a deleted tree");
        }
    }

    private void validateField(Field field) {
        if (!field.isActive()) {
            throw new HarvestDetailException("Cannot create/update harvest detail for an inactive field");
        }
    }

    private void validateTreeField(Tree tree, Field field) {
        if (!tree.getField().equals(field)) {
            throw new HarvestDetailException("Tree does not belong to the specified field");
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

    private void validateExistingHarvestDetail(HarvestDetail harvestDetail) {
        if (harvestDetail.getIsDeleted()) {
            throw new HarvestDetailException("Cannot update a deleted harvest detail");
        }
    }
}