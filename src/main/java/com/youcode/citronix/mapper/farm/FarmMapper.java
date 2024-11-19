package com.youcode.citronix.mapper.farm;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;
import com.youcode.citronix.entity.farm.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface FarmMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Farm toEntity(FarmRequest request);

    @Mapping(target = "totalFieldArea", expression = "java(farm.getTotalFieldArea())")
    @Mapping(target = "activeFieldCount", expression = "java(farm.getActiveFieldCount())")
    FarmResponse toResponse(Farm farm);

    List<FarmResponse> toResponseList(List<Farm> farms);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Farm farm, FarmRequest request);
}
