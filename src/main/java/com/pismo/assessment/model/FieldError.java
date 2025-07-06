package com.pismo.assessment.model;

import lombok.Data;

/**
 * @author shashi
 */
@Data
public class FieldError {
    private String field;
    private String message;
    private Object rejectedValue;

}
