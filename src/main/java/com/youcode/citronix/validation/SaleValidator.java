package com.youcode.citronix.validation;

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.sales.Sale;
import com.youcode.citronix.exception.sales.SaleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleValidator {

    public void validateSaleCreation(SaleRequest request, Farm farm, Harvest harvest) {
        validateBasicFields(request);
        validateSaleDate(request, harvest);
        validateQuantity(request, harvest);
        validatePrice(request);
        validateFarmAndHarvestStatus(farm, harvest);
    }

    public void validateSaleUpdate(SaleRequest request, Sale existingSale, Farm farm, Harvest harvest) {
        validateBasicFields(request);
        validateSaleDate(request, harvest);
        validateQuantity(request, harvest);
        validatePrice(request);
        validateFarmAndHarvestStatus(farm, harvest);
        validateSaleStatus(existingSale);
    }

    private void validateBasicFields(SaleRequest request) {
        if (request.getClientName() == null || request.getClientName().trim().isEmpty()) {
            throw new SaleException("Client name is required");
        }
    }

    private void validateSaleDate(SaleRequest request, Harvest harvest) {
        if (request.getSaleDate().isBefore(harvest.getHarvestDate().toLocalDate())) {
            throw new SaleException("Sale date cannot be before harvest date");
        }
    }

    private void validateQuantity(SaleRequest request, Harvest harvest) {
        if (request.getQuantity() <= 0) {
            throw new SaleException("Sale quantity must be positive");
        }
        
        if (request.getQuantity() > harvest.getTotalQuantity()) {
            throw new SaleException("Sale quantity cannot exceed harvest quantity");
        }
    }

    private void validatePrice(SaleRequest request) {
        if (request.getPricePerUnit() <= 0) {
            throw new SaleException("Price per unit must be positive");
        }
    }

    private void validateFarmAndHarvestStatus(Farm farm, Harvest harvest) {
        if (farm.getIsDeleted()) {
            throw new SaleException("Cannot create sale for a deleted farm");
        }
        if (harvest.getIsDeleted()) {
            throw new SaleException("Cannot create sale for a deleted harvest");
        }
    }

    private void validateSaleStatus(Sale sale) {
        if (sale.getIsDeleted()) {
            throw new SaleException("Cannot update a deleted sale");
        }
    }
}