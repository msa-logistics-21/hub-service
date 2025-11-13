package com.sparta.hubservice.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 각자 도메인에 맞게 에러 코드 작성
    USER_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "일치하는 회원 정보를 찾을 수 없습니다."),

    HUB_ERROR(2000, HttpStatus.INTERNAL_SERVER_ERROR, "허브 서비스 요청 중 내부 서버 오류가 발생했습니다."),
    HUB_NOT_FOUND(2001, HttpStatus.NOT_FOUND, "존재하지 않는 허브입니다."),
    HUB_DUPLICATE_NAME(2002, HttpStatus.INTERNAL_SERVER_ERROR, "중복된 허브 이름입니다."),
    HUB_NOT_AUTH(2003, HttpStatus.UNAUTHORIZED, "마스터 유저만 허브 생성/수정/삭제 작업이 가능합니다."),

    HUB_PATH_NOT_FOUND(2001, HttpStatus.NOT_FOUND, "존재하지 않는 허브입니다."),
    HUB_PATH_DUPLICATE_NAME(2002, HttpStatus.INTERNAL_SERVER_ERROR, "중복된 허브 이름입니다."),
    ;
    private final int code;
    private final HttpStatus status;
    private final String details;

}