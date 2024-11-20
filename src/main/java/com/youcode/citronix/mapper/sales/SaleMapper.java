package com.youcode.citronix.mapper.sales;

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.dto.response.sales.SaleResponse;
import com.youcode.citronix.entity.sales.Sale;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SaleMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Sale toEntity(SaleRequest request);

    @Mapping(target = "revenue", expression = "java(sale.getRevenue())")
    @Mapping(target = "farmId", source = "farm.id")
    @Mapping(target = "farmName", source = "farm.name")
    @Mapping(target = "harvestId", source = "harvest.id")
    @Mapping(target = "harvestDate", source = "harvest.harvestDate")
    SaleResponse toResponse(Sale sale);

    List<SaleResponse> toResponseList(List<Sale> sales);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Sale sale, SaleRequest request);
}
