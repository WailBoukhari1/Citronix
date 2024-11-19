package com.youcode.citronix.mapper.production;

import org.mapstruct.*;
import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;
import com.youcode.citronix.entity.production.HarvestDetail;
import java.util.List;

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
}
