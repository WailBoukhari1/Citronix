package com.youcode.citronix.service.interfaces.farm;

import java.util.List;
import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.dto.response.farm.TreeResponse;

public interface ITreeService {
    TreeResponse createTree(TreeRequest request);
    TreeResponse getTreeById(Long id);
    List<TreeResponse> getAllTrees();
    List<TreeResponse> getTreesByFieldId(Long fieldId);
    TreeResponse updateTree(Long id, TreeRequest request);
    void deleteTree(Long id);
}
