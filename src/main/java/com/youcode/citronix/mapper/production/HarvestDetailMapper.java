package com.youcode.citronix.mapper.production;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;
import com.youcode.citronix.entity.production.HarvestDetail;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface HarvestDetailMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "tree", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HarvestDetail toEntity(HarvestDetailRequest request);

    @Mapping(target = "harvestId", source = "harvest.id")
    @Mapping(target = "treeId", source = "tree.id")
    @Mapping(target = "treeName", source = "tree.name")
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "fieldName", source = "field.name")
    HarvestDetailResponse toResponse(HarvestDetail harvestDetail);

    List<HarvestDetailResponse> toResponseList(List<HarvestDetail> harvestDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "tree", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget HarvestDetail harvestDetail, HarvestDetailRequest request);
}