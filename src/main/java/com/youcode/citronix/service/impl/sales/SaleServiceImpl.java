package com.youcode.citronix.service.impl.sales;

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.dto.response.sales.SaleResponse;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.sales.Sale;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.mapper.sales.SaleMapper;
import com.youcode.citronix.repository.farm.FarmRepository;
import com.youcode.citronix.repository.production.HarvestRepository;
import com.youcode.citronix.repository.sales.SaleRepository;
import com.youcode.citronix.service.interfaces.sales.ISaleService;
import com.youcode.citronix.validation.SaleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleServiceImpl implements ISaleService {

    private final SaleRepository saleRepository;
    private final FarmRepository farmRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;
    private final SaleValidator saleValidator;

    @Override
    public SaleResponse createSale(SaleRequest request) {
        Farm farm = findFarmById(request.getFarmId());
        Harvest harvest = findHarvestById(request.getHarvestId());
        
        saleValidator.validateSaleCreation(request, farm, harvest);
        
        Sale sale = saleMapper.toEntity(request);
        sale.setFarm(farm);
        sale.setHarvest(harvest);
        
        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }

    @Override
    public SaleResponse getSaleById(Long id) {
        Sale sale = findSaleById(id);
        return saleMapper.toResponse(sale);
    }

    @Override
    public List<SaleResponse> getAllSales() {
        List<Sale> sales = saleRepository.findByIsDeletedFalse();
        return saleMapper.toResponseList(sales);
    }

    @Override
    public SaleResponse updateSale(Long id, SaleRequest request) {
        Sale existingSale = findSaleById(id);
        Farm farm = findFarmById(request.getFarmId());
        Harvest harvest = findHarvestById(request.getHarvestId());
        
        saleValidator.validateSaleUpdate(request, existingSale, farm, harvest);
        
        saleMapper.updateEntity(existingSale, request);
        existingSale.setFarm(farm);
        existingSale.setHarvest(harvest);
        
        Sale updatedSale = saleRepository.save(existingSale);
        return saleMapper.toResponse(updatedSale);
    }

    @Override
    public void deleteSale(Long id) {
        Sale sale = findSaleById(id);
        sale.setIsDeleted(true);
        saleRepository.save(sale);
    }

    private Sale findSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + id));
    }

    private Harvest findHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Harvest not found with id: " + id));
    }
}
