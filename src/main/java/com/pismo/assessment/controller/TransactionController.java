package com.pismo.assessment.controller;

import com.pismo.assessment.entity.Transaction;
import com.pismo.assessment.model.CreateTransactionRequestModel;
import com.pismo.assessment.model.TransactionResponseModel;
import com.pismo.assessment.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shashi
 */
@RestController
@RequestMapping("/api/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseModel> createTransaction(@RequestBody @Valid CreateTransactionRequestModel createTransactionRequestModel) {
        Transaction transaction = this.transactionService.createTransaction(createTransactionRequestModel);
        return ResponseEntity.ok(TransactionResponseModel.fromTransaction(transaction));
    }
}
