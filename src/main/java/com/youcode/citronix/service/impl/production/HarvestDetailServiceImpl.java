package com.youcode.citronix.service.impl.production;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.production.HarvestDetail;
import com.youcode.citronix.exception.production.HarvestDetailException;
import com.youcode.citronix.mapper.production.HarvestDetailMapper;
import com.youcode.citronix.repository.farm.TreeRepository;
import com.youcode.citronix.repository.production.HarvestDetailRepository;
import com.youcode.citronix.repository.production.HarvestRepository;
import com.youcode.citronix.service.interfaces.production.IHarvestDetailService;
import com.youcode.citronix.validation.HarvestDetailValidator;

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
public class HarvestDetailServiceImpl implements IHarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;
    private final HarvestDetailMapper harvestDetailMapper;
    private final HarvestDetailValidator harvestDetailValidator;

    @Override
    public HarvestDetailResponse createHarvestDetail(HarvestDetailRequest request, Long harvestId) {
        Harvest harvest = findHarvestById(harvestId);
        Tree tree = findTreeById(request.getTreeId());
        
        harvestDetailValidator.validateHarvestDetailCreation(request, harvest, tree);
        
        HarvestDetail harvestDetail = harvestDetailMapper.toEntity(request);
        harvestDetail.setHarvest(harvest);
        harvestDetail.setTree(tree);
        
        harvestDetail = harvestDetailRepository.save(harvestDetail);
        
        return harvestDetailMapper.toResponse(harvestDetail);
    }

    @Override
    public HarvestDetailResponse getHarvestDetailById(Long id) {
        HarvestDetail harvestDetail = findHarvestDetailById(id);
        return harvestDetailMapper.toResponse(harvestDetail);
    }

    @Override
    public PageResponse<HarvestDetailResponse> getHarvestDetailsByHarvestId(Long harvestId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Page<HarvestDetail> harvestDetailPage = harvestDetailRepository.findAllByHarvestIdAndIsDeletedFalse(
            harvestId, PageRequest.of(page, size, sort));
        
        List<HarvestDetailResponse> content = harvestDetailMapper.toResponse(harvestDetailPage.getContent());
        
        return PageResponse.<HarvestDetailResponse>builder()
            .content(content)
            .pageNo(harvestDetailPage.getNumber())
            .pageSize(harvestDetailPage.getSize())
            .totalElements(harvestDetailPage.getTotalElements())
            .totalPages(harvestDetailPage.getTotalPages())
            .last(harvestDetailPage.isLast())
            .build();
    }

    @Override
    public HarvestDetailResponse updateHarvestDetail(Long id, HarvestDetailRequest request) {
        HarvestDetail existingHarvestDetail = findHarvestDetailById(id);
        Tree tree = findTreeById(request.getTreeId());
        
        harvestDetailValidator.validateHarvestDetailUpdate(request, existingHarvestDetail, tree);
        
        existingHarvestDetail.setTree(tree);
        existingHarvestDetail.setQuantity(request.getQuantity());
        
        HarvestDetail updatedHarvestDetail = harvestDetailRepository.save(existingHarvestDetail);
        return harvestDetailMapper.toResponse(updatedHarvestDetail);
    }

    @Override
    public void deleteHarvestDetail(Long id) {
        HarvestDetail harvestDetail = findHarvestDetailById(id);
        
        if (!harvestDetail.getHarvest().getSales().isEmpty()) {
            throw new HarvestDetailException("Cannot delete harvest detail referenced in sales");
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
}