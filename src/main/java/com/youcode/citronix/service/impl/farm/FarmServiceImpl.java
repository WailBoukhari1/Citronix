package com.youcode.citronix.service.impl.farm;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.exception.farm.FarmException;
import com.youcode.citronix.mapper.farm.FarmMapper;
import com.youcode.citronix.repository.farm.FarmRepository;
import com.youcode.citronix.service.interfaces.farm.IFarmService;
import com.youcode.citronix.validation.FarmValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FarmServiceImpl implements IFarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final FarmValidator farmValidator;

    @Override
    public FarmResponse createFarm(FarmRequest request) {
        farmValidator.validateFarmCreation(request);
        
        if (farmRepository.existsByNameIgnoreCase(request.getName())) {
            throw new FarmException("Farm with name " + request.getName() + " already exists");
        }

        Farm farm = farmMapper.toEntity(request);
        farm = farmRepository.save(farm);
        return farmMapper.toResponse(farm);
    }

    @Override
    public FarmResponse getFarmById(Long id) {
        Farm farm = findFarmById(id);
        return farmMapper.toResponse(farm);
    }

    @Override
    public List<FarmResponse> getAllFarms() {
        List<Farm> farms = farmRepository.findByIsDeletedFalse();
        return farmMapper.toResponseList(farms);
    }

    @Override
    public FarmResponse updateFarm(Long id, FarmRequest request) {
        Farm existingFarm = findFarmById(id);
        farmValidator.validateFarmUpdate(existingFarm, request);

        if (!existingFarm.getName().equalsIgnoreCase(request.getName()) && 
            farmRepository.existsByNameIgnoreCase(request.getName())) {
            throw new FarmException("Farm with name " + request.getName() + " already exists");
        }

        farmMapper.updateEntity(existingFarm, request);
        Farm updatedFarm = farmRepository.save(existingFarm);
        return farmMapper.toResponse(updatedFarm);
    }

    @Override
    public void deleteFarm(Long id) {
        Farm farm = findFarmById(id);
        farm.setIsDeleted(true);
        farmRepository.save(farm);
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + id));
    }
}
