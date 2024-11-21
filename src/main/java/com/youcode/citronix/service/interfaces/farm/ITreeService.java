package com.youcode.citronix.service.interfaces.farm;

import java.util.List;
import com.youcode.citronix.dto.request.farm.TreeRequest;
import com.youcode.citronix.dto.response.farm.TreeResponse;
import org.springframework.data.domain.Page;

public interface ITreeService {
    TreeResponse createTree(TreeRequest request);
    TreeResponse getTreeById(Long id);
    PageResponse<TreeResponse> getAllTrees(int page, int size, String sortBy, String sortDir);
    PageResponse<TreeResponse> getTreesByFieldId(Long fieldId, int page, int size, String sortBy, String sortDir);
    TreeResponse updateTree(Long id, TreeRequest request);
    void deleteTree(Long id);
}
