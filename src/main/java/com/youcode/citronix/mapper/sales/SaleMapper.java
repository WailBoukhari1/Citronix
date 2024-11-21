package com.youcode.citronix.mapper.sales;

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.dto.response.sales.SaleResponse;
import com.youcode.citronix.entity.sales.Sale;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    @Mapping(target = "isDeleted", constant = "false")
    Sale toEntity(SaleRequest request);
    
    @Mapping(target = "revenue", expression = "java(sale.getRevenue())")
    @Mapping(target = "farmId", source = "farm.id")
    @Mapping(target = "farmName", source = "farm.name")
    @Mapping(target = "harvestId", source = "harvest.id")
    @Mapping(target = "harvestDate", source = "harvest.harvestDate")
    SaleResponse toResponse(Sale sale);
    
    List<SaleResponse> toResponseList(List<Sale> sales);
    
    void updateEntity(@MappingTarget Sale sale, SaleRequest request);
}
