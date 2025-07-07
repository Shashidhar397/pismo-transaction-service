package com.pismo.assessment.repository;

import com.pismo.assessment.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author shashi
 */
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

    Optional<OperationType> findByOperationTypeId(Long operationTypeId);

}
