package com.pismo.assessment.service;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.entity.OperationType;
import com.pismo.assessment.entity.Transaction;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.InvalidOperationTypeException;
import com.pismo.assessment.model.CreateTransactionRequestModel;
import com.pismo.assessment.repository.AccountRepository;
import com.pismo.assessment.repository.OperationTypeRepository;
import com.pismo.assessment.repository.TransactionRepository;
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
class TransactionServiceImplTest {

    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    private Account account;
    private OperationType operationType;
    private Transaction transaction;


    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountId(1L);
        account.setDocumentNumber("123456789");
        operationType = new OperationType();
        operationType.setOperationTypeId(1L);
        operationType.setDescription("Withdrawal");
        transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setAmount(100.0);
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transactionService = new TransactionServiceImpl(transactionRepository, accountRepository, operationTypeRepository);
    }

    @AfterEach
    void tearDown() {
        transactionService = null;
    }

    @Test
    void createTransaction_Success() {
        Mockito.when(accountRepository.findByAccountId(Mockito.anyLong()))
                .thenReturn(Optional.of(account));
        Mockito.when(operationTypeRepository.findByOperationTypeId(Mockito.anyLong()))
                .thenReturn(Optional.of(operationType));
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);

        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(1L);
        createTransactionRequestModel.setOperationTypeId(1L);
        createTransactionRequestModel.setAmount(100.0);
        Transaction createdTransaction = transactionService.createTransaction(createTransactionRequestModel);

        Assertions.assertNotNull(createdTransaction);
        Assertions.assertEquals(1L, createdTransaction.getAccount().getAccountId());
        Assertions.assertEquals(1L, createdTransaction.getOperationType().getOperationTypeId());
    }

    @Test
    void createTransaction_AccountNotFound() {
        Mockito.when(accountRepository.findByAccountId(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(1L);
        createTransactionRequestModel.setOperationTypeId(1L);
        createTransactionRequestModel.setAmount(100.0);

        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transactionService.createTransaction(createTransactionRequestModel);
        });
    }

    @Test
    void createTransaction_InvalidOperationType() {
        Mockito.when(accountRepository.findByAccountId(Mockito.anyLong()))
                .thenReturn(Optional.of(account));
        Mockito.when(operationTypeRepository.findByOperationTypeId(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(1L);
        createTransactionRequestModel.setOperationTypeId(1L);
        createTransactionRequestModel.setAmount(100.0);

        Assertions.assertThrows(InvalidOperationTypeException.class, () -> {
            transactionService.createTransaction(createTransactionRequestModel);
        });
    }

    @Test
    void createTransaction_ZeroAmount() {
        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(1L);
        createTransactionRequestModel.setOperationTypeId(1L);
        createTransactionRequestModel.setAmount(0.0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(createTransactionRequestModel);
        });
    }

}