package com.cakemonster.easyapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DateTimeType
 *
 * @author cakemonster
 * @date 2024/8/10
 */
@Getter
@AllArgsConstructor
public enum DateTimeType {

    MINUTE("minute"),
    HOUR("hour"),
    DAY("day"),
    MONTH("month"),
    YEAR("year");

    private final String type;

}
