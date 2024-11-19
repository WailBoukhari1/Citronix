package com.youcode.citronix.validation;

import org.springframework.stereotype.Component;

import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.exception.farm.TreeException;

@Component
public class TreeValidator {

    public void validateTreeCreation(TreeRequest request, Field field) {
        validateBasicFields(request);
        validateTreeInField(field);
    }

    public void validateTreeUpdate(TreeRequest request, Field field, Tree existingTree) {
        validateBasicFields(request);
        if (!existingTree.getField().getId().equals(field.getId())) {
            validateTreeInField(field);
        }
    }

    private void validateBasicFields(TreeRequest request) {
        validateName(request.getName());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new TreeException("Tree name is required");
        }
        if (name.length() < 3 || name.length() > 50) {
            throw new TreeException("Tree name must be between 3 and 50 characters");
        }
    }

    private void validateTreeInField(Field field) {
        if (!field.getFarm().canAddTrees(1)) {
            throw new TreeException("Farm has reached maximum tree capacity");
        }
    }
}
