package com.youcode.citronix.dto.response.farm;

import java.time.LocalDate;

import lombok.Value;

@Value
public class FarmResponse {
    Long id;
    String name;
    String location;
    Double area;
    LocalDate creationDate;
    Integer fieldCount;
    Double totalFieldArea;
}
