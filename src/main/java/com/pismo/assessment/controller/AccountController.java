package com.pismo.assessment.controller;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.model.*;
import com.pismo.assessment.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Account Management", description = "Operations related to account management")
@Validated
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new account",
            description = "Creates a new financial account for the specified document number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account created successfully",
                            content = @Content(schema = @Schema(implementation = AccountResponseModel.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponseModel.class))),
                    @ApiResponse(responseCode = "407", description = "Duplicate account",
                            content = @Content(schema = @Schema(implementation = ErrorResponseModel.class)))
            }
    )
    public ResponseEntity<AccountResponseModel> createAccount(
            @RequestBody @Valid CreateAccountRequestModel createAccountRequestModel) {
        Account createdAccount = this.accountService.createAccount(createAccountRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponseModel.from(createdAccount));
    }

    @GetMapping("/{account_id}")
    @Operation(
            summary = "Get account details",
            description = "Retrieves the details of an account by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account details retrieved successfully",
                            content = @Content(schema = @Schema(implementation = AccountResponseModel.class))),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseModel.class)))
            }
    )
    public ResponseEntity<AccountResponseModel> getAccount(@PathVariable("account_id") Long accountId) {
        Account account = this.accountService.getAccountById(accountId);
        return ResponseEntity.ok(AccountResponseModel.from(account));
    }

}
