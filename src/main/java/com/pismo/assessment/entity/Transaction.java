package com.pismo.assessment.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author shashi
 */
@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType operationType;

    @Column(name = "amount", nullable = false, precision = 3)
    private Double amount;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    public Transaction(Double amount, Account account, OperationType operationType) {
        this.amount = amount;
        this.account = account;
        this.operationType = operationType;
        this.eventDate = LocalDateTime.now();
    }

    public Transaction() {
    }
}
