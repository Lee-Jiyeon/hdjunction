package com.hdjunction.server.docapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PATIENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 환자를 찾을 수 없습니다."),
    HOSPITAL_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 병원을 찾을 수 없습니다."),
    VISIT_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 방문 기록입니다."),
    PATIENT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 환자입니다."),
    VISIT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 방문 기록입니다."),
    PATIENT_REGIST_NUMBER_LIMIT(HttpStatus.UNPROCESSABLE_ENTITY, "최대 등록 가능한 환자 수를 초과하였습니다.");

    private final HttpStatus status;
    private final String message;

    }
