package com.youcode.citronix.controller.farm;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.dto.response.farm.TreeResponse;
import com.youcode.citronix.service.interfaces.farm.ITreeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/trees")
@RequiredArgsConstructor
@Tag(name = "Trees", description = "Tree management endpoints")
@CrossOrigin(origins = "*")
public class TreeController {

    private final ITreeService treeService;

    @PostMapping
    @Operation(summary = "Create a new tree")
    @ApiResponse(responseCode = "201", description = "Tree created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<TreeResponse> createTree(@Valid @RequestBody TreeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(treeService.createTree(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tree by ID")
    @ApiResponse(responseCode = "200", description = "Tree found")
    @ApiResponse(responseCode = "404", description = "Tree not found")
    public ResponseEntity<TreeResponse> getTreeById(@PathVariable Long id) {
        return ResponseEntity.ok(treeService.getTreeById(id));
    }

    @GetMapping("/field/{fieldId}")
    @Operation(summary = "Get trees by field ID")
    @ApiResponse(responseCode = "200", description = "Trees retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Field not found")
    public ResponseEntity<List<TreeResponse>> getTreesByFieldId(@PathVariable Long fieldId) {
        return ResponseEntity.ok(treeService.getTreesByFieldId(fieldId));
    }

    @GetMapping
    @Operation(summary = "Get all trees")
    @ApiResponse(responseCode = "200", description = "Trees retrieved successfully")
    public ResponseEntity<List<TreeResponse>> getAllTrees() {
        return ResponseEntity.ok(treeService.getAllTrees());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update tree")
    @ApiResponse(responseCode = "200", description = "Tree updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Tree not found")
    public ResponseEntity<TreeResponse> updateTree(
            @PathVariable Long id,
            @Valid @RequestBody TreeRequest request) {
        return ResponseEntity.ok(treeService.updateTree(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tree")
    @ApiResponse(responseCode = "204", description = "Tree deleted successfully")
    @ApiResponse(responseCode = "404", description = "Tree not found")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }
}