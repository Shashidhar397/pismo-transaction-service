package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author shashi
 */
@Data
@Schema(description = "Request model for creating a new transaction")
public class CreateTransactionRequestModel {

    @NotNull(message = "Account ID is required")
    @JsonProperty("account_id")
    @Schema(description = "Account ID for the transaction", example = "123", required = true)
    private Long accountId;

    @NotNull(message = "Operation Type ID is required")
    @JsonProperty("operation_type_id")
    @Schema(description = "Type of operation (1=Purchase, 2=Installment, 3=Withdrawal, 4=Payment)",
            example = "1", required = true)
    private Long operationTypeId;

    @NotNull(message = "Amount is required")
    @Schema(description = "Transaction amount", example = "100.50", required = true)
    private Double amount;
}
