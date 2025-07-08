package com.pismo.assessment.controller;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.entity.OperationType;
import com.pismo.assessment.model.CreateTransactionRequestModel;
import com.pismo.assessment.model.ErrorResponseModel;
import com.pismo.assessment.model.TransactionResponseModel;
import com.pismo.assessment.repository.AccountRepository;
import com.pismo.assessment.repository.OperationTypeRepository;
import com.pismo.assessment.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private String baseUrl = "http://localhost:";

    private long savedAccountId;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/transactions";
        Account account = new Account();
        account.setDocumentNumber("12345678901");
        account = accountRepository.save(account);
        savedAccountId = account.getAccountId();

        OperationType operationType = new OperationType();
        operationType.setDescription("Withdrawal");
        operationType.setOperationTypeId(1L);
        operationTypeRepository.save(operationType);
    }

    @AfterEach
    void tearDown() {
        baseUrl = null;
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        operationTypeRepository.deleteAll();
    }

    @Test
    void createTransaction_shouldReturnCreatedTransaction() {
        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(savedAccountId);
        createTransactionRequestModel.setOperationTypeId(1L);
        createTransactionRequestModel.setAmount(100.0);
        ResponseEntity<TransactionResponseModel> response = restTemplate.postForEntity(this.baseUrl, createTransactionRequestModel, TransactionResponseModel.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getTransactionId());
        Assertions.assertEquals(100.0, response.getBody().getAmount());
        Assertions.assertEquals(savedAccountId, response.getBody().getAccountId());
    }

    @Test
    void createTransaction_shouldReturnBadRequestForInvalidAmount() {
        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(savedAccountId);
        createTransactionRequestModel.setOperationTypeId(1L);
        createTransactionRequestModel.setAmount(0.0);
        ResponseEntity<TransactionResponseModel> response = restTemplate.postForEntity(this.baseUrl, createTransactionRequestModel, TransactionResponseModel.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createTransaction_shouldReturnNotFoundForInvalidAccount() {
        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(999L);
        createTransactionRequestModel.setOperationTypeId(1L);
        createTransactionRequestModel.setAmount(100.0);
        ResponseEntity<ErrorResponseModel> response = restTemplate.postForEntity(this.baseUrl, createTransactionRequestModel, ErrorResponseModel.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void createTransaction_shouldReturnNotFoundForInvalidOperationType() {
        CreateTransactionRequestModel createTransactionRequestModel = new CreateTransactionRequestModel();
        createTransactionRequestModel.setAccountId(savedAccountId);
        createTransactionRequestModel.setOperationTypeId(999L);
        createTransactionRequestModel.setAmount(100.0);
        ResponseEntity<ErrorResponseModel> response = restTemplate.postForEntity(this.baseUrl, createTransactionRequestModel, ErrorResponseModel.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }
}
