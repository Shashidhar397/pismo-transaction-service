package com.pismo.assessment.repository;

import com.pismo.assessment.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author shashi
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByDocumentNumber(String documentNumber);

    Optional<Account> findByAccountId(Long accountId);

}
