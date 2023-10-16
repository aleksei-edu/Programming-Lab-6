package com.lapin.common.commands;

import com.lapin.network.AccessType;

import java.io.Serializable;

/**
 * Интерфейс, который описывает основные методы всех команд
 */

public interface Command {
    /**
     * Запускает выполнение команды
     *
     * @param arg аргументы команды (если есть, иначе null)
     */
    public void execute(String arg, Serializable argObj);

    public String toString();
    public boolean getNeedObj();
    public boolean getExecutingLocal();

    public String getName();

    public AccessType getAccessType();
}
