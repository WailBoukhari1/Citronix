package com.youcode.citronix.mapper.production;

import org.mapstruct.*;
import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.entity.production.Harvest;
import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {HarvestDetailMapper.class}
)
public interface HarvestMapper {
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "harvestDetails", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Harvest toEntity(HarvestRequest request);
    
    @Mapping(target = "totalQuantity", expression = "java(harvest.getTotalQuantity())")
    @Mapping(target = "farmId", source = "farm.id")
    @Mapping(target = "farmName", source = "farm.name")
    @Mapping(target = "harvestDetails", source = "harvestDetails")
    HarvestResponse toResponse(Harvest harvest);
    
    List<HarvestResponse> toResponse(List<Harvest> harvests);
    
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "harvestDetails", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Harvest harvest, HarvestRequest request);
}
