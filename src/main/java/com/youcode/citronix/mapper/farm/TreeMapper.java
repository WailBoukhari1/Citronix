package com.youcode.citronix.mapper.farm;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.dto.response.farm.TreeResponse;
import com.youcode.citronix.entity.farm.Tree;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "plantationDate", source = "plantationDate")
    Tree toEntity(TreeRequest request);
    
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "fieldName", source = "field.name")
    TreeResponse toResponse(Tree tree);
    
    List<TreeResponse> toResponseList(List<Tree> trees);
    
    void updateEntity(@MappingTarget Tree tree, TreeRequest request);
}