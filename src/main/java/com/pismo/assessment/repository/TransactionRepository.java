package com.pismo.assessment.repository;

import com.pismo.assessment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author shashi
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
