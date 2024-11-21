package com.youcode.citronix.service.interfaces.production;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;

public interface IHarvestDetailService {
    HarvestDetailResponse createHarvestDetail(HarvestDetailRequest request, Long harvestId);
    HarvestDetailResponse getHarvestDetailById(Long id);
    PageResponse<HarvestDetailResponse> getHarvestDetailsByHarvestId(Long harvestId, int page, int size, String sortBy, String sortDir);
    HarvestDetailResponse updateHarvestDetail(Long id, HarvestDetailRequest request);
    void deleteHarvestDetail(Long id);
}