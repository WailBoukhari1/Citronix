package com.youcode.citronix.service.interfaces.farm;

import com.youcode.citronix.dto.request.farm.FieldRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.farm.FieldResponse;

public interface IFieldService {
    FieldResponse createField(FieldRequest request);
    FieldResponse getFieldById(Long id);
    PageResponse<FieldResponse> getAllFields(int page, int size, String sortBy, String sortDir);
    PageResponse<FieldResponse> getFieldsByFarmId(Long farmId, int page, int size, String sortBy, String sortDir);
    FieldResponse updateField(Long id, FieldRequest request);
    void deleteField(Long id);
}
