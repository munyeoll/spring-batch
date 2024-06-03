package com.mun.batch.domain.enums;

import lombok.Getter;

@Getter
public enum BatchLogType {
    ERROR("ERROR"),     /* 오류 */
    SUCCESS("SUCCESS"), /* 성공 */
    FAILURE("FAILURE")  /* 실패 */
    ;

    private final String type;

    BatchLogType(String type) {
        this.type = type;
    }
}