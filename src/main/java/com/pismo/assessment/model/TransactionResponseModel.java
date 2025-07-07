package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.assessment.entity.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shashi
 */
@Data
@Schema(description = "Response model for transaction operations")
public class TransactionResponseModel {

    @JsonProperty("transaction_id")
    @Schema(description = "Unique transaction identifier", example = "789")
    private Long transactionId;

    @JsonProperty("account_id")
    @Schema(description = "Account ID", example = "123")
    private Long accountId;

    @JsonProperty("operation_type_id")
    @Schema(description = "Operation type ID", example = "1")
    private Long operationTypeId;

    @Schema(description = "Transaction amount", example = "100.50")
    private Double amount;

    public static TransactionResponseModel fromTransaction(Transaction transaction) {
        TransactionResponseModel transactionResponseModel = new TransactionResponseModel();
        transactionResponseModel.transactionId = transaction.getTransactionId();
        transactionResponseModel.accountId = transaction.getAccount().getAccountId();
        transactionResponseModel.operationTypeId = transaction.getOperationType().getOperationTypeId();
        transactionResponseModel.amount = transaction.getAmount();
        return transactionResponseModel;
    }
}
