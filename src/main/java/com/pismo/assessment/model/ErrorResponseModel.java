package com.pismo.assessment.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author shashi
 */
@Data
public class ErrorResponseModel {
    private String message;
    private int status;
    private String error;
    private LocalDateTime timestamp;
}
