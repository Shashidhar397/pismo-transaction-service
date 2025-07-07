package com.pismo.assessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author shashi
 */
@Entity
@Table(name = "operation_types")
@Data
public class OperationType {
    @Id
    @Column(name = "operation_type_id")
    private Long operationTypeId;

    private String description;
}
