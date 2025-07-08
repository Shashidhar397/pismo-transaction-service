package com.pismo.assessment.service;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.DuplicateAccountException;
import com.pismo.assessment.model.CreateAccountRequestModel;
import com.pismo.assessment.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author shashi
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class.getName());

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createAccount(CreateAccountRequestModel createAccountRequestModel) throws DuplicateAccountException {

        LOGGER.info("Creating account with document number: " + createAccountRequestModel.getDocumentNumber());

        Optional<Account> existingAccount = this.accountRepository.findByDocumentNumber(createAccountRequestModel.getDocumentNumber());
        if (existingAccount.isPresent()) {
            LOGGER.warning("Account with document number " + createAccountRequestModel.getDocumentNumber() + " already exists.");
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
