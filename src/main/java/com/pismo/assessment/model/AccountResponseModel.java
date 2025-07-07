package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.assessment.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shashi
 */
@Data
@Schema(description = "Response model for account operations")
public class AccountResponseModel {

    @Schema(description = "Unique account identifier", example = "123")
    @JsonProperty("account_id")
    private Long accountId;

    @Schema(description = "Document number associated with the account", example = "123456789")
    @JsonProperty("document_number")
    private String documentNumber;

    public static AccountResponseModel from(Account account) {
        AccountResponseModel response = new AccountResponseModel();
        response.accountId = account.getAccountId();
        response.documentNumber = account.getDocumentNumber();
        return response;
    }
}
