package com.youcode.citronix.service.interfaces.production;

import java.util.List;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;

public interface IHarvestDetailService {
    HarvestDetailResponse createHarvestDetail(HarvestDetailRequest request);
    HarvestDetailResponse getHarvestDetailById(Long id);
    List<HarvestDetailResponse> getAllHarvestDetails();
    HarvestDetailResponse updateHarvestDetail(Long id, HarvestDetailRequest request);
    void deleteHarvestDetail(Long id);
}
