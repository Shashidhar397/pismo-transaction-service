package com.pismo.assessment.exception;

import lombok.Getter;

/**
 * @author shashi
 */
@Getter
public class InvalidOperationTypeException extends RuntimeException {

    public InvalidOperationTypeException(Long operationType) {
        super("Invalid operation type: " + operationType);
    }

}
