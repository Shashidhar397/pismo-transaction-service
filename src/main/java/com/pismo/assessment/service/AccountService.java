package com.pismo.assessment.service;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.DuplicateAccountException;
import com.pismo.assessment.model.CreateAccountRequestModel;

/**
 * @author shashi
 */
public interface AccountService {

    Account createAccount(CreateAccountRequestModel createAccountRequestModel) throws DuplicateAccountException;

    Account getAccountById(Long accountId) throws AccountNotFoundException;

}
