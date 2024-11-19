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
import org.springframework.web.bind.annotation.RestController;

import com.youcode.citronix.dto.request.farm.FieldRequest;
import com.youcode.citronix.dto.response.farm.FieldResponse;
import com.youcode.citronix.service.interfaces.farm.IFieldService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
@Tag(name = "Fields", description = "Field management endpoints")
@CrossOrigin(origins = "*")
public class FieldController {

    private final IFieldService fieldService;

    @PostMapping
    @Operation(summary = "Create a new field")
    @ApiResponse(responseCode = "201", description = "Field created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<FieldResponse> createField(@Valid @RequestBody FieldRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fieldService.createField(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get field by ID")
    @ApiResponse(responseCode = "200", description = "Field found")
    @ApiResponse(responseCode = "404", description = "Field not found")
    public ResponseEntity<FieldResponse> getFieldById(@PathVariable Long id) {
        return ResponseEntity.ok(fieldService.getFieldById(id));
    }

    @GetMapping("/farm/{farmId}")
    @Operation(summary = "Get fields by farm ID")
    @ApiResponse(responseCode = "200", description = "Fields retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Farm not found")
    public ResponseEntity<List<FieldResponse>> getFieldsByFarmId(@PathVariable Long farmId) {
        return ResponseEntity.ok(fieldService.getFieldsByFarmId(farmId));
    }

    @GetMapping
    @Operation(summary = "Get all fields")
    @ApiResponse(responseCode = "200", description = "Fields retrieved successfully")
    public ResponseEntity<List<FieldResponse>> getAllFields() {
        return ResponseEntity.ok(fieldService.getAllFields());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update field")
    @ApiResponse(responseCode = "200", description = "Field updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Field not found")
    public ResponseEntity<FieldResponse> updateField(
            @PathVariable Long id,
            @Valid @RequestBody FieldRequest request) {
        return ResponseEntity.ok(fieldService.updateField(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete field")
    @ApiResponse(responseCode = "204", description = "Field deleted successfully")
    @ApiResponse(responseCode = "404", description = "Field not found")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
        return ResponseEntity.noContent().build();
    }
}
