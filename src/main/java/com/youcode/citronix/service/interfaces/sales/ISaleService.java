package com.youcode.citronix.service.interfaces.sales;

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.sales.SaleResponse;

public interface ISaleService {
    SaleResponse createSale(SaleRequest request);
    SaleResponse getSaleById(Long id);
    PageResponse<SaleResponse> getAllSales(int page, int size, String sortBy, String sortDir);
    PageResponse<SaleResponse> getSalesByHarvestId(Long harvestId, int page, int size, String sortBy, String sortDir);
    PageResponse<SaleResponse> getSalesByFarmId(Long farmId, int page, int size, String sortBy, String sortDir);
    SaleResponse updateSale(Long id, SaleRequest request);
    void deleteSale(Long id);
}
