package com.pismo.assessment.controller;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.model.AccountResponseModel;
import com.pismo.assessment.model.CreateAccountRequestModel;
import com.pismo.assessment.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shashi
 */
@RestController
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseModel> createAccount(
            @RequestBody @Valid CreateAccountRequestModel createAccountRequestModel) {
        Account createdAccount = this.accountService.createAccount(createAccountRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponseModel.from(createdAccount));
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<AccountResponseModel> getAccount(@PathVariable("account_id") Long accountId) {
        Account account = this.accountService.getAccountById(accountId);
        return ResponseEntity.ok(AccountResponseModel.from(account));
    }

}
