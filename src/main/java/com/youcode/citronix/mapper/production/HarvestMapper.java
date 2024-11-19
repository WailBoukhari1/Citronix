package com.youcode.citronix.mapper.production;

import org.mapstruct.*;
import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.entity.production.Harvest;
import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {HarvestDetailMapper.class}
)
public interface HarvestMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvestDetails", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Harvest toEntity(HarvestRequest request);

    @Named("toHarvestResponse")
    @Mapping(target = "totalQuantity", expression = "java(harvest.getTotalQuantity())")
    HarvestResponse toResponse(Harvest harvest);

    @IterableMapping(qualifiedByName = "toHarvestResponse")
    List<HarvestResponse> toResponseList(List<Harvest> harvests);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvestDetails", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Harvest harvest, HarvestRequest request);
}
