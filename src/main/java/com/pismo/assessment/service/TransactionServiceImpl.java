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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author shashi
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, OperationTypeRepository operationTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
    }


    @Override
    @Transactional
    public Transaction createTransaction(CreateTransactionRequestModel createTransactionRequestModel) throws AccountNotFoundException {
        if (createTransactionRequestModel.getAmount() == 0) {
            throw new IllegalArgumentException("Transaction amount cannot be zero");
        }
        Long accountId = createTransactionRequestModel.getAccountId();
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        Long operationTypeId = createTransactionRequestModel.getOperationTypeId();
        OperationType operationType = operationTypeRepository.findById(operationTypeId)
            .orElseThrow(() -> new InvalidOperationTypeException(operationTypeId));

        Transaction transaction = new Transaction(createTransactionRequestModel.getAmount(), account, operationType);
        return this.transactionRepository.save(transaction);
    }
}
