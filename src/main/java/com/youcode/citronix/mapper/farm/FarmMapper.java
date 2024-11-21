package com.youcode.citronix.mapper.farm;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;
import com.youcode.citronix.entity.farm.Farm;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDate.now())")
    Farm toEntity(FarmRequest request);
    
    @Mapping(target = "fieldCount", expression = "java(farm.getActiveFieldCount())")
    @Mapping(target = "totalFieldArea", expression = "java(farm.getTotalFieldArea())")
    FarmResponse toResponse(Farm farm);
    
    void updateEntity(@MappingTarget Farm farm, FarmRequest request);
}