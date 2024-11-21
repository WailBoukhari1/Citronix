package com.youcode.citronix.controller.farm;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youcode.citronix.dto.criteria.FarmSearchCriteria;
import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.service.interfaces.farm.IFarmService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/farms")
@RequiredArgsConstructor
@Tag(name = "Farms", description = "Farm management endpoints")
@CrossOrigin(origins = "*")
public class FarmController {
    private final IFarmService farmService;

    @PostMapping
    @Operation(summary = "Create farm", description = "Create a new farm")
    @ApiResponse(responseCode = "201", description = "Farm created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<FarmResponse> createFarm(@Valid @RequestBody FarmRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(farmService.createFarm(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get farm by ID")
    @ApiResponse(responseCode = "200", description = "Farm found")
    @ApiResponse(responseCode = "404", description = "Farm not found")
    public ResponseEntity<FarmResponse> getFarmById(@PathVariable Long id) {
        return ResponseEntity.ok(farmService.getFarmById(id));
    }

    @GetMapping
    @Operation(summary = "Get all farms with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Farms retrieved successfully")
    public ResponseEntity<PageResponse<FarmResponse>> getAllFarms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(farmService.getAllFarms(page, size, sortBy, sortDir));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update farm")
    @ApiResponse(responseCode = "200", description = "Farm updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Farm not found")
    public ResponseEntity<FarmResponse> updateFarm(
            @PathVariable Long id,
            @Valid @RequestBody FarmRequest request) {
        return ResponseEntity.ok(farmService.updateFarm(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete farm")
    @ApiResponse(responseCode = "204", description = "Farm deleted successfully")
    @ApiResponse(responseCode = "404", description = "Farm not found")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        farmService.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search farms with criteria, pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<PageResponse<FarmResponse>> searchFarms(
            @RequestBody FarmSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(farmService.searchFarms(criteria, page, size, sortBy, sortDir));
    }
}
