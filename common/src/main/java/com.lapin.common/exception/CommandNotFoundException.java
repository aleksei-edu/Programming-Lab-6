package com.lapin.common.exception;

/**
 * Команда не найдена в списке доступных команд.
 */
public class CommandNotFoundException extends RuntimeException {
    private static final String message = "Команда не найдена.";

    public CommandNotFoundException() {
        super(message);
    }
}
