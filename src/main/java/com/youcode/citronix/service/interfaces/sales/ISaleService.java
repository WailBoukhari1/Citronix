package com.youcode.citronix.service.interfaces.sales;

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.dto.response.sales.SaleResponse;
import java.util.List;

public interface ISaleService {
    SaleResponse createSale(SaleRequest request);
    SaleResponse getSaleById(Long id);
    List<SaleResponse> getAllSales();
    SaleResponse updateSale(Long id, SaleRequest request);
    void deleteSale(Long id);
}
