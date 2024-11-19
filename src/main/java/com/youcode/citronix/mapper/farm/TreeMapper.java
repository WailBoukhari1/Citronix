package com.youcode.citronix.mapper.farm;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.dto.response.farm.TreeResponse;
import com.youcode.citronix.entity.farm.Tree;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TreeMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Tree toEntity(TreeRequest request);

    @Named("toTreeResponse")
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "fieldName", source = "field.name")
    TreeResponse toResponse(Tree tree);

    @IterableMapping(qualifiedByName = "toTreeResponse")
    List<TreeResponse> toResponseList(List<Tree> trees);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Tree tree, TreeRequest request);
}