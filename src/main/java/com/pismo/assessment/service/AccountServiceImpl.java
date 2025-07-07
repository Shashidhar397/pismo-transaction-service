package com.pismo.assessment.service;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.DuplicateAccountException;
import com.pismo.assessment.model.CreateAccountRequestModel;
import com.pismo.assessment.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author shashi
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createAccount(CreateAccountRequestModel createAccountRequestModel) throws DuplicateAccountException {

        Optional<Account> existingAccount = this.accountRepository.findByDocumentNumber(createAccountRequestModel.getDocumentNumber());
        if (existingAccount.isPresent()) {
            throw new DuplicateAccountException(createAccountRequestModel.getDocumentNumber());
        }

        Account account = new Account();
        account.setDocumentNumber(createAccountRequestModel.getDocumentNumber());
        return this.accountRepository.save(account);
    }

    @Override
    public Account getAccountById(Long accountId) throws AccountNotFoundException {
        Optional<Account> existingAccount = this.accountRepository.findByAccountId(accountId);
        if (existingAccount.isEmpty())  {
            throw new AccountNotFoundException(accountId);
        }
        return existingAccount.get();
    }
}
