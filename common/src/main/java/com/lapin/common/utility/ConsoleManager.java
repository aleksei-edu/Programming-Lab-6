package com.lapin.common.utility;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс управляет пользовательским вводом.
 */
public class ConsoleManager {
    /**
     * Пользовательский ввод с консоли (по умолчанию).
     */
    private CommandManager commandManager;
    private static Scanner userScanner = new Scanner(System.in);

    public ConsoleManager(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    /**
     * Считывает из пользовательского ввода введенную команду.
     */
    public void interactiveMode() {
        String[] userCommand = (userScanner.nextLine().toLowerCase().trim() + " ").split(" ", 2);
        commandManager.execute(userCommand[0], userCommand[1].trim());
    }


    /**
     * Возвращает пользовательский ввод очищенный от мусора.
     *
     * @return String - из пользовательского ввода
     */
    public static String getUserPrint() {
        String userPrint = null;
        if (!userScanner.hasNext()) {
            setUserScanner(new Scanner(System.in));
            userPrint = userScanner.nextLine().trim().toLowerCase();
        } else {
            userPrint = userScanner.nextLine().trim().toLowerCase();
        }
        return userPrint;
    }

    /**
     * Изменить канал получение пользовательского ввода
     *
     * @param userScanner Scanner с пользовательским вводом
     */
    public static void setUserScanner(Scanner userScanner) {
        ConsoleManager.userScanner = userScanner;
    }

}
