package com.pismo.assessment.controller;

import com.pismo.assessment.model.AccountResponseModel;
import com.pismo.assessment.model.CreateAccountRequestModel;
import com.pismo.assessment.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    private String baseUrl = "http://localhost:";

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/accounts";
    }

    @AfterEach
    void tearDown() {
        baseUrl = null;
        accountRepository.deleteAll();
    }

    @Test
    void createAccount_shouldReturnCreatedAccount() {
        String url = this.baseUrl;
        CreateAccountRequestModel request = new CreateAccountRequestModel();
        request.setDocumentNumber("12345678901");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateAccountRequestModel> entity = new HttpEntity<>(request, headers);

        ResponseEntity<AccountResponseModel> response = restTemplate.postForEntity(url, entity, AccountResponseModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAccountId()).isNotNull();
        assertThat(response.getBody().getDocumentNumber()).isEqualTo("12345678901");
    }

    @Test
    void createAccount_shouldReturnConflictForDuplicateAccount() {
        String url = this.baseUrl;
        CreateAccountRequestModel request = new CreateAccountRequestModel();
        request.setDocumentNumber("12345678901");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateAccountRequestModel> entity = new HttpEntity<>(request, headers);

        // First call to create the account
        ResponseEntity<AccountResponseModel> firstResponse = restTemplate.postForEntity(url, entity, AccountResponseModel.class);
        assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Second call should return conflict
        ResponseEntity<AccountResponseModel> secondResponse = restTemplate.postForEntity(url, entity, AccountResponseModel.class);
        assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void getAccount_shouldReturnAccount() {
        // First, create an account
        String url = this.baseUrl;
        CreateAccountRequestModel request = new CreateAccountRequestModel();
        request.setDocumentNumber("12345678901");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateAccountRequestModel> entity = new HttpEntity<>(request, headers);
        ResponseEntity<AccountResponseModel> createResponse = restTemplate.postForEntity(url, entity, AccountResponseModel.class);

        Assertions.assertNotNull(createResponse.getBody());
        Long accountId = createResponse.getBody().getAccountId();

        // Now, get the account
        String getUrl = UriComponentsBuilder.fromUriString(url)
                .pathSegment(String.valueOf(accountId))
                .toUriString();

        ResponseEntity<AccountResponseModel> getResponse = restTemplate.getForEntity(getUrl, AccountResponseModel.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getAccountId()).isEqualTo(accountId);
        assertThat(getResponse.getBody().getDocumentNumber()).isEqualTo("12345678901");
    }

    @Test
    void getAccount_shouldReturnNotFoundForNonExistentAccount() {
        String url = UriComponentsBuilder.fromUriString(this.baseUrl)
                .pathSegment("9999") // Assuming 9999 is a non-existent account ID
                .toUriString();

        ResponseEntity<AccountResponseModel> response = restTemplate.getForEntity(url, AccountResponseModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
