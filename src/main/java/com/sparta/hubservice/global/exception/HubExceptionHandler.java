package com.sparta.hubservice.global.exception;

import com.sparta.hubservice.global.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HubExceptionHandler {

    @ExceptionHandler(HubException.class)
    public ResponseEntity<ApiResponse<?>> handleHubException(HubException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ApiResponse<>(errorCode));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity
                .status(ErrorCode.HUB_NOT_FOUND.getStatus().value())
                .body(new ApiResponse<>(ErrorCode.HUB_NOT_FOUND));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity
                .status(ErrorCode.HUB_DUPLICATE_NAME.getStatus().value())
                .body(new ApiResponse<>(ErrorCode.HUB_DUPLICATE_NAME));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneral(Exception e) {
        log.error("HUB_ERROR 발생", e);
        return ResponseEntity
                .status(ErrorCode.HUB_ERROR.getStatus().value())
                .body(new ApiResponse<>(ErrorCode.HUB_ERROR));
    }
}