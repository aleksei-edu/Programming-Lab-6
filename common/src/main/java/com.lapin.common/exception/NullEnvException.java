package com.lapin.common.exception;

/**
 * Переменная окружения не найдена.
 */
public class NullEnvException extends Exception {
    private static final String message = "Переменная окружения не найдена.";

    public NullEnvException() {
        super(message);
    }
}
