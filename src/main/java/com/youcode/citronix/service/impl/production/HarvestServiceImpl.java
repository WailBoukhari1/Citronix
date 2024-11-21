package com.youcode.citronix.service.impl.production;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.exception.production.HarvestException;
import com.youcode.citronix.mapper.production.HarvestMapper;
import com.youcode.citronix.repository.farm.FarmRepository;
import com.youcode.citronix.repository.production.HarvestRepository;
import com.youcode.citronix.service.interfaces.production.IHarvestService;
import com.youcode.citronix.validation.HarvestValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HarvestServiceImpl implements IHarvestService {

    private final HarvestRepository harvestRepository;
    private final FarmRepository farmRepository;
    private final HarvestMapper harvestMapper;
    private final HarvestValidator harvestValidator;

    @Override
    public HarvestResponse createHarvest(HarvestRequest request) {
        Farm farm = findFarmById(request.getFarmId());
        harvestValidator.validateHarvestCreation(request, farm);
        
        Harvest harvest = harvestMapper.toEntity(request);
        harvest.setFarm(farm);
        
        harvest = harvestRepository.save(harvest);
        return harvestMapper.toResponse(harvest);
    }

    @Override
    public HarvestResponse getHarvestById(Long id) {
        Harvest harvest = findHarvestById(id);
        return harvestMapper.toResponse(harvest);
    }

    @Override
    public List<HarvestResponse> getAllHarvests() {
        List<Harvest> harvests = harvestRepository.findByIsDeletedFalse();
        return harvestMapper.toResponseList(harvests);
    }

    @Override
    public HarvestResponse updateHarvest(Long id, HarvestRequest request) {
        Harvest existingHarvest = findHarvestById(id);
        Farm farm = findFarmById(request.getFarmId());
        
        harvestValidator.validateHarvestUpdate(request, existingHarvest, farm);
        
        harvestMapper.updateEntity(existingHarvest, request);
        existingHarvest.setFarm(farm);
        
        Harvest updatedHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toResponse(updatedHarvest);
    }

    @Override
    public void deleteHarvest(Long id) {
        Harvest harvest = findHarvestById(id);
        
        // Check if harvest has active harvest details
        if (!harvest.getHarvestDetails().isEmpty()) {
            throw new HarvestException("Cannot delete harvest with active harvest details. Please delete all harvest details first");
        }
        
        harvest.setIsDeleted(true);
        harvestRepository.save(harvest);
    }

    private Harvest findHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new HarvestException("Harvest not found with ID: " + id));
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new HarvestException("Farm not found with ID: " + id));
    }
}