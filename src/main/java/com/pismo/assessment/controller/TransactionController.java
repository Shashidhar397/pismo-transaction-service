package com.pismo.assessment.controller;

import com.pismo.assessment.entity.Transaction;
import com.pismo.assessment.model.CreateTransactionRequestModel;
import com.pismo.assessment.model.ErrorResponseModel;
import com.pismo.assessment.model.TransactionResponseModel;
import com.pismo.assessment.model.ValidationErrorResponseModel;
import com.pismo.assessment.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Transaction Management", description = "Operations related to financial transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new transaction",
            description = "Creates a new financial transaction for the specified account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transaction created successfully",
                            content = @Content(schema = @Schema(implementation = TransactionResponseModel.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponseModel.class))),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseModel.class)))
            }
    )
    public ResponseEntity<TransactionResponseModel> createTransaction(@RequestBody @Valid CreateTransactionRequestModel createTransactionRequestModel) {
        Transaction transaction = this.transactionService.createTransaction(createTransactionRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponseModel.fromTransaction(transaction));
    }
}
