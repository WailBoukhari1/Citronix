package com.youcode.citronix.service.interfaces.farm;

import java.util.List;

import com.youcode.citronix.dto.request.farm.FieldRequest;
import com.youcode.citronix.dto.response.farm.FieldResponse;

public interface IFieldService {
    FieldResponse createField(FieldRequest request);
    FieldResponse getFieldById(Long id);
    List<FieldResponse> getAllFields();
    List<FieldResponse> getFieldsByFarmId(Long farmId);
    FieldResponse updateField(Long id, FieldRequest request);
    void deleteField(Long id);
}
