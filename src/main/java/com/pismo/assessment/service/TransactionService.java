package com.pismo.assessment.service;

import com.pismo.assessment.entity.Transaction;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.model.CreateTransactionRequestModel;

/**
 * @author shashi
 */
public interface TransactionService {
    Transaction createTransaction(CreateTransactionRequestModel createTransactionRequestModel) throws AccountNotFoundException;
}
