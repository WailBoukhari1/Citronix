package com.youcode.citronix.controller.sales;

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

import com.youcode.citronix.dto.request.sales.SaleRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.sales.SaleResponse;
import com.youcode.citronix.service.interfaces.sales.ISaleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Sale management endpoints")
@CrossOrigin(origins = "*")
public class SaleController {

    private final ISaleService saleService;

    @PostMapping
    @Operation(
        summary = "Create a new sale",
        description = "Creates a new sale record with validation for quantity, price, and dates"
    )
    @ApiResponse(responseCode = "201", description = "Sale created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Farm or Harvest not found")
    @ApiResponse(responseCode = "409", description = "Insufficient harvest quantity")
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody SaleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saleService.createSale(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID")
    @ApiResponse(responseCode = "200", description = "Sale found")
    @ApiResponse(responseCode = "404", description = "Sale not found")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping
    @Operation(summary = "Get all sales with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Sales retrieved successfully")
    public ResponseEntity<PageResponse<SaleResponse>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(saleService.getAllSales(page, size, sortBy, sortDir));
    }

    @GetMapping("/harvest/{harvestId}")
    @Operation(summary = "Get sales by harvest ID with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Sales retrieved successfully")
    public ResponseEntity<PageResponse<SaleResponse>> getSalesByHarvestId(
            @PathVariable Long harvestId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(saleService.getSalesByHarvestId(harvestId, page, size, sortBy, sortDir));
    }

    @GetMapping("/farm/{farmId}")
    @Operation(summary = "Get sales by farm ID with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Sales retrieved successfully")
    public ResponseEntity<PageResponse<SaleResponse>> getSalesByFarmId(
            @PathVariable Long farmId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(saleService.getSalesByFarmId(farmId, page, size, sortBy, sortDir));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sale")
    @ApiResponse(responseCode = "200", description = "Sale updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Sale not found")
    public ResponseEntity<SaleResponse> updateSale(
            @PathVariable Long id,
            @Valid @RequestBody SaleRequest request) {
        return ResponseEntity.ok(saleService.updateSale(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sale")
    @ApiResponse(responseCode = "204", description = "Sale deleted successfully")
    @ApiResponse(responseCode = "404", description = "Sale not found")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
