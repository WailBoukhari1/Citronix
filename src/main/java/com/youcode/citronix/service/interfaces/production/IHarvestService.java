package com.youcode.citronix.service.interfaces.production;

import java.util.List;

import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.production.HarvestResponse;

public interface IHarvestService {
    HarvestResponse createHarvest(HarvestRequest request);
    HarvestResponse getHarvestById(Long id);
    List<HarvestResponse> getAllHarvests();
    HarvestResponse updateHarvest(Long id, HarvestRequest request);
    void deleteHarvest(Long id);
}
