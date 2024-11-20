package com.youcode.citronix.service.interfaces.farm;

import java.util.List;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;

public interface IFarmService {
    FarmResponse createFarm(FarmRequest request);
    FarmResponse getFarmById(Long id);
    List<FarmResponse> getAllFarms();
    FarmResponse updateFarm(Long id, FarmRequest request);
    void deleteFarm(Long id);
}
