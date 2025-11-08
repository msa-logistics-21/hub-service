package com.sparta.hubservice.global.exception;

import lombok.Getter;

@Getter
public class HubException extends RuntimeException {

    private final ErrorCode errorCode;

    // ErrorCode만 받는 생성자
    public HubException(ErrorCode errorCode) {
        super(errorCode.getDetails());
        this.errorCode = errorCode;
    }

    // 메시지를 추가로 지정하고 싶을 때
    public HubException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
