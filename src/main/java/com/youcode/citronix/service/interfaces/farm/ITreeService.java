package com.youcode.citronix.service.interfaces.farm;

import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.dto.response.PageResponse;
import com.youcode.citronix.dto.response.farm.TreeResponse;

public interface ITreeService {
    TreeResponse createTree(TreeRequest request);
    TreeResponse getTreeById(Long id);
    PageResponse<TreeResponse> getAllTrees(int page, int size, String sortBy, String sortDir);
    PageResponse<TreeResponse> getTreesByFieldId(Long fieldId, int page, int size, String sortBy, String sortDir);
    TreeResponse updateTree(Long id, TreeRequest request);
    void deleteTree(Long id);
}