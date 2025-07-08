package com.pismo.assessment.exception;

import com.pismo.assessment.model.ErrorResponseModel;
import com.pismo.assessment.model.FieldError;
import com.pismo.assessment.model.ValidationErrorResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shashi
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(hidden = true)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 404);
        response.put("error", "Not Found");
        response.put("message", "The requested endpoint does not exist");
        response.put("path", ex.getRequestURL());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Operation(hidden = true)
    public ResponseEntity<ErrorResponseModel> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setMessage("Malformed JSON request");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Bad Request");
        errorResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Operation(hidden = true)
    public ResponseEntity<ValidationErrorResponseModel> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        ValidationErrorResponseModel errorResponse = new ValidationErrorResponseModel();
        errorResponse.setMessage("Validation failed");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        List<FieldError> fieldErrors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            FieldError fieldError = new FieldError();
            fieldError.setField(error.getField());
            fieldError.setMessage(error.getDefaultMessage());
            fieldError.setRejectedValue(error.getRejectedValue());
            fieldErrors.add(fieldError);
        });

        errorResponse.setFieldErrors(fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Operation(hidden = true)
    public ResponseEntity<ValidationErrorResponseModel> handleConstraintViolationException(
            ConstraintViolationException ex) {

        ValidationErrorResponseModel errorResponse = new ValidationErrorResponseModel();
        errorResponse.setMessage("Validation failed");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        List<FieldError> fieldErrors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> {
            FieldError fieldError = new FieldError();
            fieldError.setField(violation.getPropertyPath().toString());
            fieldError.setMessage(violation.getMessage());
            fieldError.setRejectedValue(violation.getInvalidValue());
            fieldErrors.add(fieldError);
        });

        errorResponse.setFieldErrors(fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateAccountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Operation(hidden = true)
    public ResponseEntity<ErrorResponseModel> handleDuplicateAccountException(
            DuplicateAccountException ex) {

        ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError("Duplicate Account");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(hidden = true)
    public ResponseEntity<ErrorResponseModel> handleAccountNotFoundException(
            AccountNotFoundException ex) {

        ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError("Account Not Found");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOperationTypeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(hidden = true)
    public ResponseEntity<ErrorResponseModel> handleInvalidOperationTypeException(
            InvalidOperationTypeException ex) {

        ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError("Invalid Operation Type");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Operation(hidden = true)
    public ResponseEntity<ErrorResponseModel> handleIllegalArgumentException(IllegalArgumentException ex) {

        ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Bad Request");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Operation(hidden = true)
    public ResponseEntity<ErrorResponseModel> handleGeneralException(Exception ex) {

        ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError("Internal Server Error");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}