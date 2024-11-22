package com.youcode.citronix.service.impl.production;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.entity.enums.Season;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.production.HarvestDetail;
import com.youcode.citronix.exception.production.HarvestException;
import com.youcode.citronix.mapper.production.HarvestDetailMapper;
import com.youcode.citronix.mapper.production.HarvestMapper;
import com.youcode.citronix.repository.farm.FarmRepository;
import com.youcode.citronix.repository.farm.TreeRepository;
import com.youcode.citronix.repository.production.HarvestDetailRepository;
import com.youcode.citronix.repository.production.HarvestRepository;
import com.youcode.citronix.service.interfaces.production.IHarvestService;
import com.youcode.citronix.validation.HarvestValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HarvestServiceImpl implements IHarvestService {

    private final HarvestRepository harvestRepository;
    private final FarmRepository farmRepository;
    private final TreeRepository treeRepository;
    private final HarvestMapper harvestMapper;
    private final HarvestDetailMapper harvestDetailMapper;
    private final HarvestValidator harvestValidator;
    private final HarvestDetailRepository harvestDetailRepository;

    @Override
    public HarvestResponse createHarvest(HarvestRequest request) {
        Farm farm = findFarmById(request.getFarmId());
        harvestValidator.validateHarvestCreation(request, farm);
        
        Harvest harvest = harvestMapper.toEntity(request);
        harvest.setFarm(farm);
        harvest = harvestRepository.save(harvest);
        
        if (request.getHarvestDetails() != null && !request.getHarvestDetails().isEmpty()) {
            for (HarvestDetailRequest detailRequest : request.getHarvestDetails()) {
                Tree tree = findTreeById(detailRequest.getTreeId());
                validateTreeForHarvest(tree, harvest);
                
                HarvestDetail detail = harvestDetailMapper.toEntity(detailRequest);
                detail.setTree(tree);
                detail.setHarvest(harvest);
                harvestDetailRepository.save(detail);
                harvest.getHarvestDetails().add(detail); 
            }
        }
        
        return harvestMapper.toResponse(harvest);
    }

    @Override
    public HarvestResponse getHarvestById(Long id) {
        Harvest harvest = findHarvestById(id);
        return harvestMapper.toResponse(harvest);
    }

    @Override
    public PageResponse<HarvestResponse> getAllHarvests(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Page<Harvest> harvestPage = harvestRepository.findAllByIsDeletedFalse(PageRequest.of(page, size, sort));
        List<HarvestResponse> content = harvestMapper.toResponse(harvestPage.getContent());
        
        return PageResponse.<HarvestResponse>builder()
            .content(content)
            .pageNo(harvestPage.getNumber())
            .pageSize(harvestPage.getSize())
            .totalElements(harvestPage.getTotalElements())
            .totalPages(harvestPage.getTotalPages())
            .last(harvestPage.isLast())
            .build();
    }

    @Override
    public HarvestResponse updateHarvest(Long id, HarvestRequest request) {
        Harvest existingHarvest = findHarvestById(id);
        Farm farm = findFarmById(request.getFarmId());
        
        harvestValidator.validateHarvestUpdate(request, existingHarvest, farm);
        
        existingHarvest.setFarm(farm);
        existingHarvest.setHarvestDate(request.getHarvestDate());
        existingHarvest.setSeason(request.getSeason());
        existingHarvest.setDescription(request.getDescription());
        
        Harvest updatedHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toResponse(updatedHarvest);
    }

    @Override
    public void deleteHarvest(Long id) {
        Harvest harvest = findHarvestById(id);
        
        if (!harvest.getSales().isEmpty()) {
            throw new HarvestException("Cannot delete harvest with associated sales");
        }
        
        harvest.setIsDeleted(true);
        harvestRepository.save(harvest);
    }

    @Override
    public Double getTotalHarvestQuantity(Long farmId, Season season) {
        Farm farm = findFarmById(farmId);
        return farm.getHarvests().stream()
            .filter(h -> !h.getIsDeleted() && h.getSeason() == season)
            .mapToDouble(Harvest::getTotalQuantity)
            .sum();
    }

    private Harvest findHarvestById(Long id) {
        return harvestRepository.findById(id)
            .orElseThrow(() -> new HarvestException("Harvest not found with ID: " + id));
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
            .orElseThrow(() -> new HarvestException("Farm not found with ID: " + id));
    }

    private Tree findTreeById(Long id) {
        return treeRepository.findById(id)
            .orElseThrow(() -> new HarvestException("Tree not found with ID: " + id));
    }

    private void validateTreeForHarvest(Tree tree, Harvest harvest) {
        if (harvestRepository.existsByTreeAndSeasonAndIsDeletedFalse(tree, harvest.getSeason())) {
            throw new HarvestException("Tree already harvested this season");
        }
    }
}
