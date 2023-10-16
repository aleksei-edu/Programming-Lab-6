package com.lapin.common.exception;

/**
 * Команда не принимает аргументы, но ей был передан аргумент.
 */
public class CommandNotAcceptArgumentsException extends RuntimeException {
    private static final String message = "Эта команда не принимает аргументов.";

    public CommandNotAcceptArgumentsException() {
        super(message);
    }
}
