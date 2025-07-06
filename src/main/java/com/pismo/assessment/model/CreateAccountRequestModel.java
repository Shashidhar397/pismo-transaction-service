package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author shashi
 */
@Data
public class CreateAccountRequestModel {

    @JsonProperty("document_number")
    @NotNull(message = "Document number is required")
    @NotBlank(message = "Document number is required")
    @Pattern(regexp = "\\d{11}", message = "Document number must be 11 digits")
    @Valid
    private String documentNumber;

}
