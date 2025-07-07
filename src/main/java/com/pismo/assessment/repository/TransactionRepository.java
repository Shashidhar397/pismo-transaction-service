package com.pismo.assessment.repository;

import com.pismo.assessment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shashi
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
