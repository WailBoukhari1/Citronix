package com.youcode.citronix.service.interfaces.production;

import java.util.List;

import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.entity.enums.Season;

public interface IHarvestService {
    HarvestResponse createHarvest(HarvestRequest request);
    HarvestResponse getHarvestById(Long id);
    PageResponse<HarvestResponse> getAllHarvests(int page, int size, String sortBy, String sortDir);
    HarvestResponse updateHarvest(Long id, HarvestRequest request);
    void deleteHarvest(Long id);
    Double getTotalHarvestQuantity(Long farmId, Season season);
}