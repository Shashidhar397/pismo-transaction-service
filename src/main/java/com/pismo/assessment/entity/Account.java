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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;
}
