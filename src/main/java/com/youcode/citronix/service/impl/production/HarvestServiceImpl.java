package com.youcode.citronix.service.impl.production;

import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.production.HarvestDetail;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.exception.production.HarvestException;
import com.youcode.citronix.mapper.production.HarvestMapper;
import com.youcode.citronix.repository.farm.FieldRepository;
import com.youcode.citronix.repository.farm.TreeRepository;
import com.youcode.citronix.repository.production.HarvestRepository;
import com.youcode.citronix.service.interfaces.production.IHarvestService;
import com.youcode.citronix.validation.HarvestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HarvestServiceImpl implements IHarvestService {

    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;
    private final HarvestMapper harvestMapper;
    private final HarvestValidator harvestValidator;

    @Override
    public HarvestResponse createHarvest(HarvestRequest request) {
        harvestValidator.validateHarvestCreation(request);
        
        Harvest harvest = harvestMapper.toEntity(request);
        harvest.setHarvestDetails(createHarvestDetails(request, harvest));
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
        harvestValidator.validateHarvestUpdate(request, existingHarvest);
        
        harvestMapper.updateEntity(existingHarvest, request);
        existingHarvest.setHarvestDetails(createHarvestDetails(request, existingHarvest));
        Harvest updatedHarvest = harvestRepository.save(existingHarvest);
        
        return harvestMapper.toResponse(updatedHarvest);
    }

    @Override
    public void deleteHarvest(Long id) {
        Harvest harvest = findHarvestById(id);
        harvest.setIsDeleted(true);
        harvestRepository.save(harvest);
    }

    private List<HarvestDetail> createHarvestDetails(HarvestRequest request, Harvest harvest) {
        List<HarvestDetail> details = new ArrayList<>();
        
        request.getHarvestDetails().forEach(detailRequest -> {
            Tree tree = findTreeById(detailRequest.getTreeId());
            Field field = findFieldById(detailRequest.getFieldId());
            
            if (!field.equals(tree.getField())) {
                throw new HarvestException("Tree does not belong to the specified field");
            }

            harvestValidator.validateTreeForHarvest(tree, harvest.getHarvestDate());

            HarvestDetail detail = HarvestDetail.builder()
                    .harvest(harvest)
                    .tree(tree)
                    .field(field)
                    .quantity(detailRequest.getQuantity())
                    .build();
            
            details.add(detail);
        });
        
        return details;
    }

    private Harvest findHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Harvest not found with id: " + id));
    }

    private Tree findTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tree not found with id: " + id));
    }

    private Field findFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + id));
    }
}
