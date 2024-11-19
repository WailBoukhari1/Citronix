package com.youcode.citronix.service.impl.farm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
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

import jakarta.persistence.criteria.Predicate;
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
        List<Farm> farms = farmRepository.findAll();
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

    @Override
    public List<FarmResponse> searchFarms(String name, String location, Double minArea, Double maxArea) {
        Specification<Farm> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (location != null && !location.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }

            if (minArea != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("area"), minArea));
            }

            if (maxArea != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("area"), maxArea));
            }

            predicates.add(cb.isFalse(root.get("isDeleted")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Farm> farms = farmRepository.findAll(spec);
        return farmMapper.toResponseList(farms);
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + id));
    }
}
