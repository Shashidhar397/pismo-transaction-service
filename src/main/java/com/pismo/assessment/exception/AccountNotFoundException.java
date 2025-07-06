package com.pismo.assessment.exception;

/**
 * @author shashi
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long accountId) {
        super("Account with ID " + accountId + " not found");
    }
}
