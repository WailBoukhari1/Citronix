package com.youcode.citronix.service.impl.sales;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.sales.SaleResponse;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.sales.Sale;
import com.youcode.citronix.exception.sales.SaleException;
import com.youcode.citronix.mapper.sales.SaleMapper;
import com.youcode.citronix.repository.farm.FarmRepository;
import com.youcode.citronix.repository.production.HarvestRepository;
import com.youcode.citronix.repository.sales.SaleRepository;
import com.youcode.citronix.service.interfaces.sales.ISaleService;
import com.youcode.citronix.validation.SaleValidator;

import lombok.RequiredArgsConstructor;

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
    public PageResponse<SaleResponse> getAllSales(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Sale> salePage = saleRepository.findByIsDeletedFalse(pageable);
        Page<SaleResponse> responsePage = salePage.map(saleMapper::toResponse);
        
        return PageResponse.fromPage(responsePage);
    }

    @Override
    public PageResponse<SaleResponse> getSalesByHarvestId(Long harvestId, int page, int size, String sortBy, String sortDir) {
        Harvest harvest = findHarvestById(harvestId);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Sale> salePage = saleRepository.findByHarvestAndIsDeletedFalse(harvest, pageable);
        Page<SaleResponse> responsePage = salePage.map(saleMapper::toResponse);
        
        return PageResponse.fromPage(responsePage);
    }

    @Override
    public PageResponse<SaleResponse> getSalesByFarmId(Long farmId, int page, int size, String sortBy, String sortDir) {
        Farm farm = findFarmById(farmId);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Sale> salePage = saleRepository.findByFarmAndIsDeletedFalse(farm, pageable);
        Page<SaleResponse> responsePage = salePage.map(saleMapper::toResponse);
        
        return PageResponse.fromPage(responsePage);
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
                .orElseThrow(() -> new SaleException("Sale not found with ID: " + id));
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new SaleException("Farm not found with ID: " + id));
    }

    private Harvest findHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new SaleException("Harvest not found with ID: " + id));
    }
}
