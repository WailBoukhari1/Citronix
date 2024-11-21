package com.youcode.citronix.mapper.production;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;
import com.youcode.citronix.entity.production.HarvestDetail;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "tree", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HarvestDetail toEntity(HarvestDetailRequest request);
    
    @Mapping(target = "harvestId", source = "harvest.id")
    @Mapping(target = "treeId", source = "tree.id")
    @Mapping(target = "treeName", source = "tree.name")
    @Mapping(target = "treeProductivity", source = "tree.productivity")
    HarvestDetailResponse toResponse(HarvestDetail harvestDetail);
    
    List<HarvestDetailResponse> toResponse(List<HarvestDetail> harvestDetails);
    
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "tree", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget HarvestDetail harvestDetail, HarvestDetailRequest request);
}