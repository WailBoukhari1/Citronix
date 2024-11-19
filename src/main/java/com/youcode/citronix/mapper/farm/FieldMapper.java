package com.youcode.citronix.mapper.farm;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.youcode.citronix.dto.request.farm.FieldRequest;
import com.youcode.citronix.dto.response.farm.FieldResponse;
import com.youcode.citronix.entity.farm.Field;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FieldMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    
    Field toEntity(FieldRequest request);

    @Named("toFieldResponse")
    @Mapping(target = "farmId", source = "farm.id")
    @Mapping(target = "farmName", source = "farm.name")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "area", source = "area")
    @Mapping(target = "isDeleted", source = "isDeleted")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    FieldResponse toResponse(Field field);

    @IterableMapping(qualifiedByName = "toFieldResponse")
    List<FieldResponse> toResponseList(List<Field> fields);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(@MappingTarget Field field, FieldRequest request);
}
