package com.youcode.citronix.service.impl.production;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.production.HarvestDetail;
import com.youcode.citronix.exception.production.HarvestDetailException;
import com.youcode.citronix.mapper.production.HarvestDetailMapper;
import com.youcode.citronix.repository.farm.FieldRepository;
import com.youcode.citronix.repository.farm.TreeRepository;
import com.youcode.citronix.repository.production.HarvestDetailRepository;
import com.youcode.citronix.repository.production.HarvestRepository;
import com.youcode.citronix.service.interfaces.production.IHarvestDetailService;
import com.youcode.citronix.validation.HarvestDetailValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HarvestDetailServiceImpl implements IHarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;
    private final HarvestDetailMapper harvestDetailMapper;
    private final HarvestDetailValidator harvestDetailValidator;

    @Override
    public HarvestDetailResponse createHarvestDetail(HarvestDetailRequest request) {
        Harvest harvest = findHarvestById(request.getHarvestId());
        Tree tree = findTreeById(request.getTreeId());
        Field field = findFieldById(request.getFieldId());

        harvestDetailValidator.validateHarvestDetailCreation(request, harvest, tree, field);

        HarvestDetail harvestDetail = harvestDetailMapper.toEntity(request);
        harvestDetail.setHarvest(harvest);
        harvestDetail.setTree(tree);
        harvestDetail.setField(field);

        harvestDetail = harvestDetailRepository.save(harvestDetail);
        return harvestDetailMapper.toResponse(harvestDetail);
    }

    @Override
    public HarvestDetailResponse getHarvestDetailById(Long id) {
        HarvestDetail harvestDetail = findHarvestDetailById(id);
        return harvestDetailMapper.toResponse(harvestDetail);
    }

    @Override
    public List<HarvestDetailResponse> getAllHarvestDetails() {
        List<HarvestDetail> harvestDetails = harvestDetailRepository.findByIsDeletedFalse();
        return harvestDetailMapper.toResponseList(harvestDetails);
    }

    @Override
    public HarvestDetailResponse updateHarvestDetail(Long id, HarvestDetailRequest request) {
        HarvestDetail existingHarvestDetail = findHarvestDetailById(id);
        Harvest harvest = findHarvestById(request.getHarvestId());
        Tree tree = findTreeById(request.getTreeId());
        Field field = findFieldById(request.getFieldId());

        harvestDetailValidator.validateHarvestDetailUpdate(request, existingHarvestDetail, harvest, tree, field);

        harvestDetailMapper.updateEntity(existingHarvestDetail, request);
        existingHarvestDetail.setHarvest(harvest);
        existingHarvestDetail.setTree(tree);
        existingHarvestDetail.setField(field);

        HarvestDetail updatedHarvestDetail = harvestDetailRepository.save(existingHarvestDetail);
        return harvestDetailMapper.toResponse(updatedHarvestDetail);
    }

    @Override
    public void deleteHarvestDetail(Long id) {
        HarvestDetail harvestDetail = findHarvestDetailById(id);
        
        // Check if harvest detail is referenced in any sales
        if (!harvestDetail.getHarvest().getSales().isEmpty()) {
            throw new HarvestDetailException("Cannot delete harvest detail that is referenced in sales. Please delete associated sales first");
        }
        
        harvestDetail.setIsDeleted(true);
        harvestDetailRepository.save(harvestDetail);
    }

    private HarvestDetail findHarvestDetailById(Long id) {
        return harvestDetailRepository.findById(id)
                .orElseThrow(() -> new HarvestDetailException("Harvest detail not found with ID: " + id));
    }

    private Harvest findHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new HarvestDetailException("Harvest not found with ID: " + id));
    }

    private Tree findTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new HarvestDetailException("Tree not found with ID: " + id));
    }

    private Field findFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new HarvestDetailException("Field not found with ID: " + id));
    }
}
