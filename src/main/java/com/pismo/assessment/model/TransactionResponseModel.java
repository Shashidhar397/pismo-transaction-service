package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.assessment.entity.Transaction;
import lombok.Data;

/**
 * @author shashi
 */
@Data
public class TransactionResponseModel {

    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type_id")
    private Long operationTypeId;

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
