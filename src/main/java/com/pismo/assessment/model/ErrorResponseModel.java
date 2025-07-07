package com.pismo.assessment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author shashi
 */
@Data
@Schema(description = "Error response model")
public class ErrorResponseModel {
    @Schema(description = "Error message", example = "Account with ID 123 not found")
    private String message;

    @Schema(description = "Error code", example = "400")
    private int status;

    @Schema(description = "Error type", example = "Bad Request")
    private String error;

    @Schema(description = "Timestamp of the error", example = "2023-10-01T12:00:00Z")
    private LocalDateTime timestamp;
}
