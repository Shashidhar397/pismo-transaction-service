package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shashi
 */
@Data
@Schema(description = "Model representing a field error in validation")
public class FieldError {

    @Schema(description = "Name of the field that caused the error", example = "document_number")
    private String field;

    @Schema(description = "Error message describing the validation issue", example = "Document number must be 11 digits")
    private String message;

    @Schema(description = "Rejected value that caused the validation error", example = "123456789")
    @JsonProperty("rejected_value")
    private Object rejectedValue;

}
