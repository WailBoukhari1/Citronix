package com.youcode.citronix.service.impl.farm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youcode.citronix.dto.criteria.FarmSearchCriteria;
import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.exception.farm.FarmException;
import com.youcode.citronix.mapper.farm.FarmMapper;
import com.youcode.citronix.repository.farm.FarmRepository;
import com.youcode.citronix.service.interfaces.farm.IFarmService;
import com.youcode.citronix.specification.FarmSpecification;
import com.youcode.citronix.validation.FarmValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FarmServiceImpl implements IFarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final FarmValidator farmValidator;
    private final FarmSpecification farmSpecification;

    @Override
    public FarmResponse createFarm(FarmRequest request) {
        farmValidator.validateFarmCreation(request);
        
        if (farmRepository.existsByNameIgnoreCase(request.getName())) {
            throw new FarmException("Farm with name '" + request.getName() + "' already exists");
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
    public PageResponse<FarmResponse> getAllFarms(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Farm> farmPage = farmRepository.findByIsDeletedFalse(pageable);
        Page<FarmResponse> responsePage = farmPage.map(farmMapper::toResponse);
        
        return PageResponse.fromPage(responsePage);
    }

    @Override
    public FarmResponse updateFarm(Long id, FarmRequest request) {
        Farm existingFarm = findFarmById(id);
        farmValidator.validateFarmUpdate(existingFarm, request);

        if (!existingFarm.getName().equalsIgnoreCase(request.getName()) && 
            farmRepository.existsByNameIgnoreCase(request.getName())) {
            throw new FarmException("Farm with name '" + request.getName() + "' already exists");
        }

        farmMapper.updateEntity(existingFarm, request);
        Farm updatedFarm = farmRepository.save(existingFarm);
        return farmMapper.toResponse(updatedFarm);
    }

    @Override
    public void deleteFarm(Long id) {
        Farm farm = findFarmById(id);
        
        // Check if farm has active fields
        if (!farm.getFields().isEmpty()) {
            throw new FarmException("Cannot delete farm with active fields. Please delete all fields first");
        }
        
        farm.setIsDeleted(true);
        farmRepository.save(farm);
    }

    @Override
    public PageResponse<FarmResponse> searchFarms(FarmSearchCriteria criteria, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Farm> spec = FarmSpecification.withCriteria(criteria);
        
        Page<Farm> farmPage = farmRepository.findAll(spec, pageable);
        Page<FarmResponse> responsePage = farmPage.map(farmMapper::toResponse);
        
        return PageResponse.fromPage(responsePage);
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new FarmException("Farm not found with ID: " + id));
    }
}
