package com.youcode.citronix.validation;

import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.exception.farm.TreeException;
import org.springframework.stereotype.Component;

@Component
public class TreeValidator {

    public void validateTreeCreation(TreeRequest request, Field field) {
        validateCommon(request);
        validateField(field);
    }

    public void validateTreeUpdate(TreeRequest request, Field field, Tree existingTree) {
        validateCommon(request);
        validateField(field);
        validateExistingTree(existingTree);
    }

    private void validateCommon(TreeRequest request) {
        if (request == null) {
            throw new TreeException("Tree request cannot be null");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new TreeException("Tree name cannot be null or empty");
        }
        if (request.getFieldId() == null) {
            throw new TreeException("Field ID cannot be null");
        }
    }

    private void validateField(Field field) {
        if (!field.isActive()) {
            throw new TreeException("Cannot create/update tree for an inactive field");
        }
        if (field.getIsDeleted()) {
            throw new TreeException("Cannot create/update tree for a deleted field");
        }
    }

    private void validateExistingTree(Tree tree) {
        if (tree.getIsDeleted()) {
            throw new TreeException("Cannot update a deleted tree");
        }
    }
}