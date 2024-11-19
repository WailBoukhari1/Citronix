package com.youcode.citronix.controller.production;

import com.youcode.citronix.dto.request.production.HarvestRequest;
import com.youcode.citronix.dto.response.production.HarvestResponse;
import com.youcode.citronix.service.interfaces.production.IHarvestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/harvests")
@RequiredArgsConstructor
@Tag(name = "Harvests", description = "Harvest management endpoints")
@CrossOrigin(origins = "*")
public class HarvestController {

    private final IHarvestService harvestService;

    @PostMapping
    @Operation(summary = "Create a new harvest")
    @ApiResponse(responseCode = "201", description = "Harvest created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<HarvestResponse> createHarvest(@Valid @RequestBody HarvestRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(harvestService.createHarvest(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get harvest by ID")
    @ApiResponse(responseCode = "200", description = "Harvest found")
    @ApiResponse(responseCode = "404", description = "Harvest not found")
    public ResponseEntity<HarvestResponse> getHarvestById(@PathVariable Long id) {
        return ResponseEntity.ok(harvestService.getHarvestById(id));
    }

    @GetMapping
    @Operation(summary = "Get all harvests")
    @ApiResponse(responseCode = "200", description = "Harvests retrieved successfully")
    public ResponseEntity<List<HarvestResponse>> getAllHarvests() {
        return ResponseEntity.ok(harvestService.getAllHarvests());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update harvest")
    @ApiResponse(responseCode = "200", description = "Harvest updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Harvest not found")
    public ResponseEntity<HarvestResponse> updateHarvest(
            @PathVariable Long id,
            @Valid @RequestBody HarvestRequest request) {
        return ResponseEntity.ok(harvestService.updateHarvest(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete harvest")
    @ApiResponse(responseCode = "204", description = "Harvest deleted successfully")
    @ApiResponse(responseCode = "404", description = "Harvest not found")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}
