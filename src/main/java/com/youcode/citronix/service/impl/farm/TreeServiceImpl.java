package com.youcode.citronix.service.impl.farm;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.dto.response.farm.TreeResponse;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.HarvestDetail;
import com.youcode.citronix.exception.farm.TreeException;
import com.youcode.citronix.mapper.farm.TreeMapper;
import com.youcode.citronix.repository.farm.FieldRepository;
import com.youcode.citronix.repository.farm.TreeRepository;
import com.youcode.citronix.repository.production.HarvestDetailRepository;
import com.youcode.citronix.service.interfaces.farm.ITreeService;
import com.youcode.citronix.validation.TreeValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TreeServiceImpl implements ITreeService {

    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;
    private final TreeMapper treeMapper;
    private final TreeValidator treeValidator;
    private final HarvestDetailRepository harvestDetailRepository;

    @Override
    public TreeResponse createTree(TreeRequest request) {
        Field field = findFieldById(request.getFieldId());
        treeValidator.validateTreeCreation(request, field);

        if (treeRepository.existsByNameIgnoreCaseAndFieldId(request.getName(), request.getFieldId())) {
            throw new TreeException("Tree with name '" + request.getName() + "' already exists in this field");
        }

        Tree tree = treeMapper.toEntity(request);
        tree.setField(field);
        tree = treeRepository.save(tree);
        return treeMapper.toResponse(tree);
    }

    @Override
    public TreeResponse getTreeById(Long id) {
        Tree tree = findTreeById(id);
        return treeMapper.toResponse(tree);
    }

    @Override
    public List<TreeResponse> getAllTrees() {
        List<Tree> trees = treeRepository.findByIsDeletedFalse();
        return treeMapper.toResponseList(trees);
    }

    @Override
    public List<TreeResponse> getTreesByFieldId(Long fieldId) {
        Field field = findFieldById(fieldId);
        List<Tree> trees = treeRepository.findByFieldAndIsDeletedFalse(field);
        return treeMapper.toResponseList(trees);
    }

    @Override
    public TreeResponse updateTree(Long id, TreeRequest request) {
        Tree existingTree = findTreeById(id);
        Field field = findFieldById(request.getFieldId());
        
        treeValidator.validateTreeUpdate(request, field, existingTree);

        if (!existingTree.getName().equalsIgnoreCase(request.getName()) && 
            treeRepository.existsByNameIgnoreCaseAndFieldId(request.getName(), request.getFieldId())) {
            throw new TreeException("Tree with name '" + request.getName() + "' already exists in this field");
        }

        treeMapper.updateEntity(existingTree, request);
        existingTree.setField(field);
        Tree updatedTree = treeRepository.save(existingTree);
        return treeMapper.toResponse(updatedTree);
    }

    @Override
    public void deleteTree(Long id) {
        Tree tree = findTreeById(id);
        
        // Check if tree has active harvest details
        List<HarvestDetail> harvestDetails = harvestDetailRepository.findByTreeIdAndIsDeletedFalse(tree.getId());
        if (!harvestDetails.isEmpty()) {
            throw new TreeException("Cannot delete tree with active harvest details. Please delete all harvest details first");
        }
        
        tree.setIsDeleted(true);
        treeRepository.save(tree);
    }

    private Tree findTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new TreeException("Tree not found with ID: " + id));
    }

    private Field findFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new TreeException("Field not found with ID: " + id));
    }
}