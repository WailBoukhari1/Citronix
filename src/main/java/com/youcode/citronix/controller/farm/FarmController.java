package com.youcode.citronix.controller.farm;

import com.youcode.citronix.dto.request.farm.FarmRequest;
import com.youcode.citronix.dto.response.farm.FarmResponse;
import com.youcode.citronix.service.interfaces.farm.IFarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farms")
@RequiredArgsConstructor
@Tag(name = "Farms", description = "Farm management endpoints")
@CrossOrigin(origins = "*")
public class FarmController {

    private final IFarmService farmService;

    @PostMapping
    @Operation(summary = "Create a new farm", description = "Creates a new farm with the provided details")
    @ApiResponse(responseCode = "201", description = "Farm created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<FarmResponse> createFarm(@Valid @RequestBody FarmRequest farmRequest) {
        FarmResponse response = farmService.createFarm(farmRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all farms", description = "Retrieves a list of all active farms")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved farms list")
    public ResponseEntity<List<FarmResponse>> getAllFarms() {
        return ResponseEntity.ok(farmService.getAllFarms());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get farm by ID", description = "Retrieves a specific farm by its ID")
    @ApiResponse(responseCode = "200", description = "Farm found")
    @ApiResponse(responseCode = "404", description = "Farm not found")
    public ResponseEntity<FarmResponse> getFarmById(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(farmService.getFarmById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update farm", description = "Updates an existing farm with new information")
    @ApiResponse(responseCode = "200", description = "Farm updated successfully")
    @ApiResponse(responseCode = "404", description = "Farm not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<FarmResponse> updateFarm(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody FarmRequest farmRequest) {
        return ResponseEntity.ok(farmService.updateFarm(id, farmRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete farm", description = "Soft deletes a farm by its ID")
    @ApiResponse(responseCode = "204", description = "Farm deleted successfully")
    @ApiResponse(responseCode = "404", description = "Farm not found")
    public ResponseEntity<Void> deleteFarm(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long id) {
        farmService.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search farms", description = "Search farms with optional filters")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<FarmResponse>> searchFarms(
            @Parameter(description = "Farm name (optional)")
            @RequestParam(required = false) String name,
            @Parameter(description = "Farm location (optional)")
            @RequestParam(required = false) String location,
            @Parameter(description = "Minimum area in square meters (optional)")
            @RequestParam(required = false) Double minArea,
            @Parameter(description = "Maximum area in square meters (optional)")
            @RequestParam(required = false) Double maxArea) {
        return ResponseEntity.ok(farmService.searchFarms(name, location, minArea, maxArea));
    }
}
