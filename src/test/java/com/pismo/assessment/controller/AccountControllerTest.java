package com.pismo.assessment.controller;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.DuplicateAccountException;
import com.pismo.assessment.model.CreateAccountRequestModel;
import com.pismo.assessment.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountId(1L);
        account.setDocumentNumber("12345678901");

        Mockito.when(accountService.createAccount(Mockito.any(CreateAccountRequestModel.class))).thenReturn(account);
        Mockito.when(accountService.getAccountById(Mockito.anyLong())).thenReturn(account);
    }

    @AfterEach
    void tearDown() {
        account = null;
        Mockito.reset(accountService);
    }

    @Test
    void createAccount() throws Exception {
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document_number\":\"12345678901\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(1L))
                .andExpect(jsonPath("$.document_number").value("12345678901"));
    }

    @Test
    void createAccount_InvalidDocumentNumber() throws Exception {
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document_number\":\"invalid\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAccount_DuplicateDocumentNumber() throws Exception {
        Mockito.when(accountService.createAccount(Mockito.any(CreateAccountRequestModel.class)))
                .thenThrow(new DuplicateAccountException("12345678901"));

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document_number\":\"12345678901\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    void getAccount() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/{account_id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(1L))
                .andExpect(jsonPath("$.document_number").value("12345678901"));
    }

    @Test
    void getAccount_NotFound() throws Exception {
        Mockito.when(accountService.getAccountById(Mockito.anyLong())).thenThrow(new AccountNotFoundException(999L));

        mockMvc.perform(get("/api/v1/accounts/{account_id}", 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}