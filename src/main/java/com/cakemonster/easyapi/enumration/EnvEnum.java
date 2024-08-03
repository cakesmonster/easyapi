package com.cakemonster.easyapi.enumration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * EnvEnum
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Getter
@AllArgsConstructor
public enum EnvEnum {
    TEST,
    PRE,
    PROD;
}
