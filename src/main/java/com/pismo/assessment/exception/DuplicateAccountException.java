package com.pismo.assessment.exception;

import lombok.Getter;

/**
 * @author shashi
 */
@Getter
public class DuplicateAccountException extends RuntimeException {

    public DuplicateAccountException(String documentNumber) {
        super("Account with document number " + documentNumber + " already exists");
    }

}
