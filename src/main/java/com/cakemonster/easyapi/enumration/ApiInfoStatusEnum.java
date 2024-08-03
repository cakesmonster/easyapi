package com.cakemonster.easyapi.enumration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ApiInfoStatus
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Getter
@AllArgsConstructor
public enum ApiInfoStatusEnum {

    UNPUBLISHED("UNPUBLISHED", 0),
    PENDING_TESTING("PENDING_TESTING", 1),
    PUBLISHED("PUBLISHED", 2),
    OFFLINE("OFFLINE", 3);

    private final String info;
    private final int code;

    public static ApiInfoStatusEnum ofValue(int code) {
        for (ApiInfoStatusEnum value : ApiInfoStatusEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
