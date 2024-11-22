package com.youcode.citronix.service.interfaces.farm;

import com.youcode.citronix.dto.criteria.FarmSearchCriteria;
import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.farm.FarmResponse;

public interface IFarmService {
    FarmResponse createFarm(FarmRequest request);
    FarmResponse getFarmById(Long id);
    PageResponse<FarmResponse> getAllFarms(int page, int size, String sortBy, String sortDir);
    FarmResponse updateFarm(Long id, FarmRequest request);
    void deleteFarm(Long id);
    PageResponse<FarmResponse> searchFarms(FarmSearchCriteria criteria, int page, int size, String sortBy, String sortDir);
}
