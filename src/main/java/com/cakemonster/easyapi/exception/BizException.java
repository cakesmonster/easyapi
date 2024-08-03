package com.cakemonster.easyapi.exception;

/**
 * BizException
 *
 * @author cakemonster
 * @date 2024/8/3
 */
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }

    public BizException(long code, String message) {
        super(message);
    }

}
