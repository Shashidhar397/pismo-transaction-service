package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author shashi
 */
@Data
@Schema(description = "Request model for creating a new account")
public class CreateAccountRequestModel {

    @NotNull(message = "Document number is required")
    @NotBlank(message = "Document number is required")
    @Pattern(regexp = "\\d{11}", message = "Document number must be 11 digits")
    @Valid
    @Schema(description = "Document number of the account holder", example = "12345678901", required = true)
    @JsonProperty("document_number")
    private String documentNumber;

}
