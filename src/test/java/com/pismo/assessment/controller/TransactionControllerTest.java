package com.pismo.assessment.controller;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.entity.OperationType;
import com.pismo.assessment.entity.Transaction;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.InvalidOperationTypeException;
import com.pismo.assessment.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        account.setAccountId(1L);
        account.setDocumentNumber("12345678901");

        OperationType operationType = new OperationType();
        operationType.setOperationTypeId(1L);
        operationType.setDescription("Withdrawal");

        transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setAmount(100.0);
        transaction.setAccount(account); // Assuming account is set later
        transaction.setOperationType(operationType); // Assuming operation type is set later

        // Mocking the service methods
        Mockito.when(transactionService.createTransaction(Mockito.any())).thenReturn(transaction);
    }

    @AfterEach
    void tearDown() {
        transaction = null;
        Mockito.reset(transactionService);
    }

    @Test
    void createTransaction() throws Exception {
        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account_id\":1,\"operation_type_id\":1,\"amount\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    void createTransaction_InvalidAmount() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any()))
                .thenThrow(new IllegalArgumentException("Transaction amount cannot be zero"));
        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account_id\":1,\"operation_type_id\":1,\"amount\":0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTransaction_AccountNotFound() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any()))
                .thenThrow(new AccountNotFoundException(1L));

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account_id\":1,\"operation_type_id\":1,\"amount\":100.0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTransaction_InvalidOperationType() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any()))
                .thenThrow(new InvalidOperationTypeException(1L));

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account_id\":1,\"operation_type_id\":1,\"amount\":100.0}"))
                .andExpect(status().isNotFound());
    }
}