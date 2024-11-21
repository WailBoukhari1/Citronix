package com.youcode.citronix.repository.farm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long>, JpaSpecificationExecutor<Tree> {
    boolean existsByNameIgnoreCaseAndFieldId(String name, Long fieldId);
    Page<Tree> findByIsDeletedFalse(Pageable pageable);
    Page<Tree> findByFieldAndIsDeletedFalse(Field field, Pageable pageable);
}
