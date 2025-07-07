package com.pismo.assessment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shashi
 */
@Data
@Schema(description = "Validation error response model")
public class ValidationErrorResponseModel {
    @Schema(description = "Error message", example = "Bad Request")
    private String message;

    @Schema(description = "Error code", example = "400")
    private int status;

    @Schema(description = "List of validation errors")
    private List<FieldError> fieldErrors;

    @Schema(description = "Timestamp of the error", example = "2023-10-01T12:00:00Z")
    private LocalDateTime timestamp = LocalDateTime.now();
}
