package com.youcode.citronix.mapper.farm;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.youcode.citronix.dto.request.farm.FieldRequest;
import com.youcode.citronix.dto.response.farm.FieldResponse;
import com.youcode.citronix.entity.farm.Field;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    @Mapping(target = "isDeleted", constant = "false")
    Field toEntity(FieldRequest request);
    
    @Mapping(target = "farmId", source = "farm.id")
    @Mapping(target = "farmName", source = "farm.name")
    FieldResponse toResponse(Field field);
    
    List<FieldResponse> toResponseList(List<Field> fields);
    
    void updateEntity(@MappingTarget Field field, FieldRequest request);
}
