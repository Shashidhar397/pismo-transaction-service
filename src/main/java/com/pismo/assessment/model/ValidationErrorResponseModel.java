package com.pismo.assessment.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shashi
 */
@Data
public class ValidationErrorResponseModel {
    private String message;
    private int status;
    private List<FieldError> fieldErrors;
    private LocalDateTime timestamp = LocalDateTime.now();
}
