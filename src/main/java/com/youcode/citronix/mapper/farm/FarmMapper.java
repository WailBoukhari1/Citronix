package com.youcode.citronix.mapper.farm;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;
import com.youcode.citronix.entity.farm.Farm;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FarmMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fields", ignore = true)
    @Mapping(target = "harvests", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDate.now())")
    Farm toEntity(FarmRequest request);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "area", source = "area")
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "fieldCount", expression = "java(farm.getActiveFieldCount())")
    @Mapping(target = "totalFieldArea", expression = "java(farm.getTotalFieldArea())")
    FarmResponse toResponse(Farm farm);
    
    List<FarmResponse> toResponseList(List<Farm> farms);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fields", ignore = true)
    @Mapping(target = "harvests", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    void updateEntity(@MappingTarget Farm farm, FarmRequest request);
}