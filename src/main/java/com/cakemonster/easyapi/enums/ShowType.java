package com.cakemonster.easyapi.enums;

import lombok.Getter;

/**
 * ShowTypeEnum
 *
 * @author cakemonster
 * @date 2024/7/28
 */
@Getter
public enum ShowType {

    LINE("line"),
    BAR("bar"),
    PIE("pie"),
    OVERVIEW("overview"),
    LINE_LIST("lineList");

    private final String type;

    ShowType(String type) {
        this.type = type;
    }

}
