package com.youcode.citronix.repository.farm;

import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
    boolean existsByNameIgnoreCaseAndFieldId(String name, Long fieldId);
    List<Tree> findByFieldAndIsDeletedFalse(Field field);
    List<Tree> findByIsDeletedFalse();
}
