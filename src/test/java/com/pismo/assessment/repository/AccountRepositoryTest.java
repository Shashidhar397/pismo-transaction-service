package com.pismo.assessment.repository;

import com.pismo.assessment.entity.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Long savedAccountId;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        account.setDocumentNumber("123456789");
        account = accountRepository.save(account);
        savedAccountId = account.getAccountId();
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void findByDocumentNumber_Found() {
        String documentNumber = "123456789";
        Account account = accountRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new AssertionError("Account not found"));

        assertNotNull(account);
        assertEquals(documentNumber, account.getDocumentNumber());
    }

    @Test
    void findByDocumentNumber_NotFound() {
        String documentNumber = "987654321";
        assertFalse(accountRepository.findByDocumentNumber(documentNumber).isPresent());
    }


    @Test
    void findByAccountId_Found() {
        Optional<Account> found = accountRepository.findByAccountId(this.savedAccountId);
        assertTrue(found.isPresent());
    }

    @Test
    void findByAccountId_NotFound() {
        Long accountId = 999L; // Assuming this ID does not exist
        assertFalse(accountRepository.findByAccountId(accountId).isPresent());
    }
}