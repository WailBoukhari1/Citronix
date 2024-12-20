package com.youcode.citronix.service.impl.farm;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youcode.citronix.dto.request.farm.FieldRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.farm.FieldResponse;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.exception.farm.FieldException;
import com.youcode.citronix.mapper.farm.FieldMapper;
import com.youcode.citronix.repository.farm.FarmRepository;
import com.youcode.citronix.repository.farm.FieldRepository;
import com.youcode.citronix.service.interfaces.farm.IFieldService;
import com.youcode.citronix.validation.FieldValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FieldServiceImpl implements IFieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;
    private final FieldValidator fieldValidator;

    @Override
    public FieldResponse createField(FieldRequest request) {
        Farm farm = findFarmById(request.getFarmId());
        fieldValidator.validateFieldCreation(request, farm);

        if (fieldRepository.existsByNameIgnoreCaseAndFarmId(request.getName(), request.getFarmId())) {
            throw new FieldException("Field with name '" + request.getName() + "' already exists in this farm");
        }

        Field field = fieldMapper.toEntity(request);
        field.setFarm(farm);
        field.setTrees(new ArrayList<>());
        field = fieldRepository.save(field);
        return fieldMapper.toResponse(field);
    }

    @Override
    public FieldResponse getFieldById(Long id) {
        Field field = findFieldById(id);
        return fieldMapper.toResponse(field);
    }

    @Override
    public PageResponse<FieldResponse> getAllFields(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Field> fieldPage = fieldRepository.findByIsDeletedFalse(pageable);
        Page<FieldResponse> responsePage = fieldPage.map(fieldMapper::toResponse);

        return PageResponse.fromPage(responsePage);
    }

    @Override
    public PageResponse<FieldResponse> getFieldsByFarmId(Long farmId, int page, int size, String sortBy, String sortDir) {
        Farm farm = findFarmById(farmId);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Field> fieldPage = fieldRepository.findByFarmAndIsDeletedFalse(farm, pageable);
        Page<FieldResponse> responsePage = fieldPage.map(fieldMapper::toResponse);

        return PageResponse.fromPage(responsePage);
    }

    @Override
    public FieldResponse updateField(Long id, FieldRequest request) {
        Field existingField = findFieldById(id);
        Farm farm = findFarmById(request.getFarmId());

        fieldValidator.validateFieldUpdate(request, farm, existingField);

        if (!existingField.getName().equalsIgnoreCase(request.getName())
                && fieldRepository.existsByNameIgnoreCaseAndFarmId(request.getName(), request.getFarmId())) {
            throw new FieldException("Field with name '" + request.getName() + "' already exists in this farm");
        }

        fieldMapper.updateEntity(existingField, request);
        existingField.setFarm(farm);
        Field updatedField = fieldRepository.save(existingField);
        return fieldMapper.toResponse(updatedField);
    }

    @Override
    public void deleteField(Long id) {
        Field field = findFieldById(id);

        // Check if field has active trees
        if (!field.getTrees().isEmpty()) {
            throw new FieldException("Cannot delete field with active trees. Please delete all trees first");
        }

        field.setIsDeleted(true);
        fieldRepository.save(field);
    }

    private Field findFieldById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new FieldException("Field not found with ID: " + id));
    }

    private Farm findFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new FieldException("Farm not found with ID: " + id));
    }
}
