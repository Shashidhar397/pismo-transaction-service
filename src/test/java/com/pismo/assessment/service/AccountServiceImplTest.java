package com.pismo.assessment.service;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.DuplicateAccountException;
import com.pismo.assessment.model.CreateAccountRequestModel;
import com.pismo.assessment.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountRepository);
        account = new Account();
        account.setAccountId(1L);
        account.setDocumentNumber("123456789");
    }

    @AfterEach
    void tearDown() {
        accountService = null;
    }

    @Test
    void createAccount_Success() {
        Mockito.mock(AccountRepository.class);

        Mockito.when(accountRepository.findByDocumentNumber(Mockito.anyString())).thenReturn(Optional.empty());

        Mockito.when(accountRepository.save(Mockito.any(Account.class)))
                .thenReturn(account);

        CreateAccountRequestModel createAccountRequestModel = new CreateAccountRequestModel();
        createAccountRequestModel.setDocumentNumber("123456789");

        Account createdAccount = accountService.createAccount(createAccountRequestModel);
        Assertions.assertNotNull(createdAccount);
        Assertions.assertEquals("123456789", createdAccount.getDocumentNumber());

    }

    @Test
    void createAccount_DuplicateAccount() {
        Mockito.when(accountRepository.findByDocumentNumber(Mockito.anyString()))
                .thenReturn(Optional.of(account));

        CreateAccountRequestModel createAccountRequestModel = new CreateAccountRequestModel();
        createAccountRequestModel.setDocumentNumber("123456789");

        Assertions.assertThrows(DuplicateAccountException.class, () -> {
            accountService.createAccount(createAccountRequestModel);
        });
    }

    @Test
    void getAccountById_Success() {
        Mockito.when(accountRepository.findByAccountId(Mockito.anyLong()))
                .thenReturn(Optional.of(account));

        Long accountId = 1L;

        Account foundAccount = accountService.getAccountById(accountId);

        Assertions.assertNotNull(foundAccount);
        Assertions.assertEquals(accountId, foundAccount.getAccountId());
        Assertions.assertEquals("123456789", foundAccount.getDocumentNumber());
    }

    @Test
    void getAccountById_NotFound() {
        Mockito.when(accountRepository.findByAccountId(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Long accountId = 1L;

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(accountId));
    }
}