package com.youcode.citronix.controller.production;

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

import com.youcode.citronix.dto.request.production.HarvestDetailRequest;
import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.production.HarvestDetailResponse;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.service.interfaces.production.IHarvestDetailService;
import com.youcode.citronix.service.interfaces.production.IHarvestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/harvests")
@RequiredArgsConstructor
@Tag(name = "Harvests", description = "Harvest and harvest details management endpoints")
@CrossOrigin(origins = "*")
public class HarvestController {

    private final IHarvestService harvestService;
    private final IHarvestDetailService harvestDetailService;

    @PostMapping
    @Operation(summary = "Create new harvest with optional details")
    @ApiResponse(responseCode = "201", description = "Harvest created successfully")
    public ResponseEntity<HarvestResponse> createHarvest(@Valid @RequestBody HarvestRequest request) {
        return new ResponseEntity<>(harvestService.createHarvest(request), HttpStatus.CREATED);
    }

    @PostMapping("/{harvestId}/details")
    @Operation(summary = "Add harvest detail to existing harvest")
    @ApiResponse(responseCode = "201", description = "Harvest detail added successfully")
    public ResponseEntity<HarvestDetailResponse> addHarvestDetail(
            @PathVariable Long harvestId,
            @Valid @RequestBody HarvestDetailRequest request) {
        return new ResponseEntity<>(harvestDetailService.createHarvestDetail(request, harvestId), HttpStatus.CREATED);
    }

    @GetMapping("/{harvestId}/details")
    @Operation(summary = "Get all harvest details for a harvest")
    public ResponseEntity<PageResponse<HarvestDetailResponse>> getHarvestDetails(
            @PathVariable Long harvestId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(harvestDetailService.getHarvestDetailsByHarvestId(harvestId, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get harvest by ID")
    @ApiResponse(responseCode = "200", description = "Harvest found")
    @ApiResponse(responseCode = "404", description = "Harvest not found")
    public ResponseEntity<HarvestResponse> getHarvestById(@PathVariable Long id) {
        return ResponseEntity.ok(harvestService.getHarvestById(id));
    }

    @GetMapping
    @Operation(summary = "Get all harvests with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Harvests retrieved successfully")
    public ResponseEntity<PageResponse<HarvestResponse>> getAllHarvests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(harvestService.getAllHarvests(page, size, sortBy, sortDir));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update harvest")
    @ApiResponse(responseCode = "200", description = "Harvest updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Harvest or Farm not found")
    @ApiResponse(responseCode = "409", description = "Harvest already exists for this season")
    public ResponseEntity<HarvestResponse> updateHarvest(
            @PathVariable Long id,
            @Valid @RequestBody HarvestRequest request) {
        return ResponseEntity.ok(harvestService.updateHarvest(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete harvest")
    @ApiResponse(responseCode = "204", description = "Harvest deleted successfully")
    @ApiResponse(responseCode = "404", description = "Harvest not found")
    @ApiResponse(responseCode = "409", description = "Cannot delete harvest with active harvest details")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}
