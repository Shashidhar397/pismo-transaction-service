package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.assessment.entity.Account;
import lombok.Data;

/**
 * @author shashi
 */
@Data
public class AccountResponse {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("document_number")
    private String documentNumber;

    public static AccountResponse from(Account account) {
        AccountResponse response = new AccountResponse();
        response.accountId = account.getId();
        response.documentNumber = account.getDocumentNumber();
        return response;
    }
}
