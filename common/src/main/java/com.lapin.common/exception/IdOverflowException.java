package com.lapin.common.exception;

/**
 * Произошло переполнение id и нового уникального id не найти.
 */
public class IdOverflowException extends Exception {
    private static final String message = "Свободного id не найдено";

    public IdOverflowException() {
        super(message);
    }
}
