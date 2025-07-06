package com.pismo.assessment.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author shashi
 */
@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;
}
