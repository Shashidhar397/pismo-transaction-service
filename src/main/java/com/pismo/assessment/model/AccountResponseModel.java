package com.pismo.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.assessment.entity.Account;
import lombok.Data;

/**
 * @author shashi
 */
@Data
public class AccountResponseModel {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("document_number")
    private String documentNumber;

    public static AccountResponseModel from(Account account) {
        AccountResponseModel response = new AccountResponseModel();
        response.accountId = account.getAccountId();
        response.documentNumber = account.getDocumentNumber();
        return response;
    }
}
