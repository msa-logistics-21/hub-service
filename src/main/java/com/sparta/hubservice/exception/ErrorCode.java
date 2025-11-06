package com.sparta.hubservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    HUB_ERROR(2000, HttpStatus.INTERNAL_SERVER_ERROR, "허브 에러 발생"),
    HUB_JSON_PROCESSING_EXCEPTION(2001, HttpStatus.INTERNAL_SERVER_ERROR, "허브 응답 객체를 JSON으로 변환하지 못했습니다"),
    ;
    private final int code;
    private final HttpStatus status;
    private final String details;

}